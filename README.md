# avaliacao-java

[![License](http://img.shields.io/:license-MIT.svg)](https://mit-license.org/)

## Requisitos

Para build e execução da aplicação, você vai precisar:

- [JDK 11](http://www.oracle.com/technetwork/java/javase/downloads)
- [Maven 3](https://maven.apache.org)

## Rodando a aplicação localmente

Existem diversas formas de executar uma aplicação Spring Boot localmente em sua maquina. Um dos métodos é a execução do método `main` na classe `br.com.confitec.teste.TesteApplication` a partir de sua IDE.


Alternativamente, você pode usar o [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html):

```shell
mvn spring-boot:run
```

## Documentação API

A api conta com documentação automatizada no padrão OAS3. Para verificação de endpoints, rotas, especificação de objetos de envio e retorno, acesse:
 - [Teste Confitec - OAS3](http://localhost:8081/swagger-ui/index.html)


## Copyright

[MIT License](https://mit-license.org/)