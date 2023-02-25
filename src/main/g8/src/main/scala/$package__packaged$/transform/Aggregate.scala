package $package$.transform

import $package$.model.Client
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.sql.functions._

object Aggregate {

  def computeSumInvoicesByCLient(clients: Dataset[Client], invoices: DataFrame): DataFrame = {
    clients
      .join(invoices, "clientId")
      .agg(sum("invoiceAmount"))
  }
}
