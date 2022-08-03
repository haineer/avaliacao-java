# Avaliação Java II
O valor pago por uma apólice de seguro é composto, basicamente, pela soma das coberturas contratadas. Com base nisso, elabore uma solução capaz de retornar o plano de pagamento de uma apólice de acordo com as opções solicitadas, aplicando ou não um percentual de juros no parcelamento.

A implementação desse teste deve ser disponibilizada em um repositório Git com acesso público.

## Regras de negócio
O resto da divisão do rateio é alocado na primeira parcela;
A taxa de juros deverá ser aplicado ao valor total (soma das coberturas), utilizando a regra juros compostos:
### P = V*(1 + i)^t, onde:
- P = Valor a ser pago;
- V = Valor total;
- i = Taxa de juros;
- t = Quantidade de parcelas.
> O parcelamento não se aplica para taxas de juros ou quantidade de parcelas negativas.

## Detalhes técnicos
- Endpoint: */confitec/teste/parcelamento*;
- Método: *POST*;
- Request*: *https://pastecode.io/s/fz606jgw*;
- Response*: *https://pastecode.io/s/0x8w819p*;
- Projeto base: *https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.7.1&packaging=jar&jvmVersion=11&groupId=br.com.confitec&artifactId=teste&name=teste&description=Avalia%C3%A7%C3%A3o%20Java&packageName=br.com.confitec.teste&dependencies=web,devtools,lombok*;
- A implementação de testes unitários, bem como a utilização de princípios SOLID, são diferenciais para a avaliação.
> \* Talvez seja necessário criar uma conta na plataforma *PasteCode.io* para acessar o conteúdo.
