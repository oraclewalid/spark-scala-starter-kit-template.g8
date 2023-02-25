package $package$.write

import $package$.config.Config.RdsConfig
import org.apache.spark.sql.DataFrame

import java.util.Properties

object Write {

  def writeClientsToRDS(clients: DataFrame, rdsConfig: RdsConfig) = {

      val connectionProperties = new Properties()
      connectionProperties.put("user", rdsConfig.login)

      clients
        .write
        .jdbc(rdsConfig.connexionUrl, "client_table", connectionProperties)
  }
}
