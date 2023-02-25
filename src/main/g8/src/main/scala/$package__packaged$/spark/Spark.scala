package $package$.spark

import org.apache.spark.sql.SparkSession

object Spark {

  var spark: Option[SparkSession] = None

  /**
   * Create SparkSession for production purpose, all parameters must be specified
   * in spark-submit, see examples in readme.md
   *
   * @return
   */
  def getOrCreate(): SparkSession = {
    if (spark.isEmpty) {
      spark = Some(
        SparkSession
          .builder()
          .getOrCreate()
      )
    }
    spark.get
  }

}
