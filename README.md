<div align="center" padding=25px>
    <img src="images/confluent.png" width=50% height=50%>
</div>

# <div align="center">Multi-region Consumer Example</div>

This repo contains an example consumer application which can be consume messages from a topic which is replicated between two Kafka clusters with offset translation. The simple idea is: if the consumer consumed a message from `topic` from `cluster-a`, the replicated message in `topic` on `cluster-b` **will not** be consumed if the consumer consumes from the replicated topic.

This repo only contains the consumer code to demonstrate this. The rest of the content can be found [here](#) in a more comprehensive demonstration. 

> **NOTE**: This repo is meant for you to use if you want the consumer code itself to play around with. The demonstration above uses this code packaged in a docker image.

> **NOTE**: You'll want Maven, Java, and Docker installed on your machine in order to make any use of this.

## Usage

The following describe the configurable properties the consumer uses as is. The most useful is the likely the config file. 

| Env Variable  | Default                    | Required | Description                                                |
|---------------|----------------------------|:--------:|------------------------------------------------------------|
| `TOPIC`       | "topic"                    |     N    | The topic the consumer will consume from.                  |
| `GROUP_ID`    | "multi-region-consumer"    |     N    | The consumer group ID used by the consumer.                |
| `CONFIG_FILE` | "/config/setup.properties" |     N    | The location of a properties file for the consumer to use. |

> **NOTE**: An example `setup.properties` file can be found in this directory to use as reference. 






