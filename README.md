# Commands

  * Java: `11.0.6`

  * Scala: `2.13.1`

  * Make: `4.3`

## Server

  * Start

      * `make server host=<host ip> port=<port value>`  or `make server`

          * default host is `127.0.0.1`

          * default port is `8080`

  * Stop

      * `<ctrl> + c`

## Client

  * Start

      * `make client host=<host ip> port=<port value>`  or `make client`

          * default host is `127.0.0.1`

          * default port is `8080`

## Improvement

  * Use assembly plugin to create a fat jar for deployment or some other operations

# References

  [1]. [sbt-assembly](https://github.com/sbt/sbt-assembly)
