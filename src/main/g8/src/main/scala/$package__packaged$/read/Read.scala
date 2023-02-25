package $package$.read

import $package$.model.Client
import $package$.spark.Spark
import org.apache.spark.sql.functions.{concat, lit, rand}
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

object Read {

  def readClientsDataset(path: String)(implicit sparkSession: SparkSession): Dataset[Client] = {
    import sparkSession.implicits._

    sparkSession
      .range(1, 100000)
      .toDF("clientId")
      .withColumn("email", concat('clientId, lit("@mail.com")))
      .as[Client]
  }

  def readInvoicesDataframe(path: String)(implicit sparkSession: SparkSession): DataFrame = {
    sparkSession
      .range(1, 100000)
      .toDF("clientId")
      .withColumn("invoiceAmount", rand())
  }
}
