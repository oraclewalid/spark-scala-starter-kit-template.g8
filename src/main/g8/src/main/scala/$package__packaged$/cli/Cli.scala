package $package$.cli

import org.apache.log4j.{LogManager, Logger}

object Cli {

  val LOG = LogManager.getLogger("Cli")

  case class CliParams(env: String = "dev")

  def getCliParams(args: Array[String]) : CliParams = {
    import scopt.OParser
    val builder = OParser.builder[CliParams]
    val parser = {
      import builder._
      OParser.sequence(
        programName("scopt"),
        head("scopt", "4.x"),
        builder.opt[String]('e', "env")
          .action((x, c) => c.copy(env = x))
          .validate(_ match {
            case "dev" | "prod" => Right()
            case _              => Left("Environment must be development or production")
          })
          .text("Environment must be development or production"),
      )
    }

    // OParser.parse returns Option[Config]
    OParser.parse(parser, args, CliParams()) match {
      case Some(params) =>  params
      case _            => {
        LOG.error("Error in argument parsing")
        sys.exit(1)
      }
    }
  }
}