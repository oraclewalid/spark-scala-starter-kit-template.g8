package $package$

import com.github.mrpowers.spark.fast.tests.DatasetComparer
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import org.apache.spark.sql.types._
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._

class HelloSpec extends AnyFlatSpec with Matchers with SparkSessionTestWrapper with DatasetComparer {

  import spark.implicits._

  "Simple test" should "work without spark" in {
  	val hello = "hello"
    hello shouldEqual "hello"
  }

  "Spark test" should "work with spark" in {

      val sourceDF = Seq(
        ("jose"),
        ("li"),
        ("luisa")
      ).toDF("name")

      val actualDF = sourceDF.select(col("name").alias("student"))

      val expectedDF = Seq(
        ("jose"),
        ("li"),
        ("luisa")
      ).toDF("student")

      assertSmallDatasetEquality(actualDF, expectedDF)
  }
}
