package $package$.config

import $package$.cli.Cli.CliParams
import com.typesafe.config.ConfigFactory
import org.apache.log4j.LogManager
import pureconfig._
import pureconfig.generic.auto._

object Config {

  val LOG = LogManager.getLogger("Config")

  case class RdsConfig(connexionUrl: String, login: String)
  case class AwsConfig(bucket: String)
  case class AwsVaultConfig(roleARN: String, roleName: String, clientRegion: String, roleSessionName: String, stsEndpointUri: URI, vaultUri: URI, vaultAuthenticationUri: URI)

  def conf(implicit cliParams: CliParams) = {
    ConfigFactory
      .systemEnvironment()
      .withFallback(ConfigFactory.parseResources(s"application-\${cliParams.env}.conf"))
      .withFallback(ConfigFactory.load())
      .resolve();
  }

  def rdsConfig(implicit cliParams: CliParams): RdsConfig  = ConfigSource.fromConfig(conf.getConfig("rds")).load[RdsConfig].getOrElse {
    LOG.error("Error in conf mapping")
    sys.exit(1)
  }

  def awsConfig(implicit cliParams: CliParams): AwsConfig = ConfigSource.fromConfig(conf.getConfig("aws")).load[AwsConfig].getOrElse {
    LOG.error("Error in conf mapping")
    sys.exit(1)
  }

  def vaultConfig(implicit cliParams: CliParams): AwsVaultConfig = ConfigSource.fromConfig(conf.getConfig("vault")).load[AwsVaultConfig].getOrElse {
    LOG.error("Error in vault conf mapping")
    sys.exit(1)
  }
}
