package $package$

import org.apache.spark.sql.SparkSession
import org.apache.log4j.{LogManager, Logger}
import $package$.spark.Spark._
import $package$.cli.Cli
import $package$.config.Config
import $package$.read.Read
import $package$.spark.Spark
import $package$.write.Write
import $package$.transform.Aggregate

import com.decathlon.behaviour.vault.VaultAwsRoleAuthentication

object Main {

  val LOG = LogManager.getLogger("Cli")

  def main(args: Array[String]): Unit = {
  	
    implicit val cliParams = Cli.getCliParams(args)

    val vaultConfig = Config.vaultConfig
    val vaultAuthentication = new VaultAwsRoleAuthentication(vaultConfig)
    val vault  = vaultAuthentication.getVaultClient.getOrElse {
      LOG.error("Error in vault authentication")
      sys.exit(1)
    }

        // To write into vault
    val secrets = new util.HashMap[String, AnyRef]()
    secrets.put("secretKey" ,"secretValue")
    vault
      .logical()
      .write("secret/test", secrets)

    val mySecretFromVault = vault
              .logical
              .read("secret/test")
              .getData
              .get("foo")

    LOG.info(s"Vault is authenticated on ${vaultConfig.vaultUri} with user : ${vault.auth().unwrap().getUserId}")

    implicit val sparkSession = Spark.getOrCreate()

    val clients = Read.readClientsDataset("/path/to/my/dataset/clients")
    val invoices = Read.readInvoicesDataframe("/path/to/my/dataset/invoices")

    val aggDf = Aggregate.computeSumInvoicesByCLient(clients, invoices)

    Write.writeClientsToRDS(aggDf, Config.rdsConfig)


    sparkSession.close()


  }
}