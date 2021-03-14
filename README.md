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

  * Stop

      * `<ctrl> + c`

## Future Improvements

  * Use assembly plugin to create a fat jar for deployment or some other operations

  * Use `scope`[2] to manage resource release lifecyle, preventing `Channel has reached the end of stream` issue

# References

  [1]. [sbt-assembly](https://github.com/sbt/sbt-assembly)

  [2]. [Flexible Resource Scoping](https://zio.github.io/zio-nio/docs/essentials/essentials_resources)
