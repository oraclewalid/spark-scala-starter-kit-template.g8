package $package$

import $package$
import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfterAll, Suite}

trait SparkSessionTestWrapper extends BeforeAndAfterAll { this: Suite =>

  lazy val spark = SparkSession
      .builder()
      .master("local")
      .appName("spark testing session")
      .config("spark.sql.shuffle.partitions", "1")
      .getOrCreate()

  override def afterAll() = {
    spark.stop()
  }
}