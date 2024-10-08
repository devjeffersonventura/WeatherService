# WeatherService

WeatherService é um aplicativo Java que utiliza a API do OpenWeather para obter informações sobre o clima atual, previsão do tempo e alertas meteorológicos para uma cidade especificada.

## Funcionalidades

- Obter a temperatura atual de uma cidade.
- Obter a previsão do tempo para os próximos 5 dias.
- Obter alertas meteorológicos para uma cidade.

## Pré-requisitos

- Java 11 ou superior
- Biblioteca Gson (disponível em https://jarcasting.com/artifacts/com.google.code.gson/gson/2.8.6/)
- Uma chave de API do OpenWeatherMap (https://openweathermap.org/)

## Instalação

1. Clone o repositório:
    ```sh
    git clone https://github.com/devjeffersonventura/WeatherService.git
    cd WeatherService
    ```

2. Adicione a biblioteca Gson ao seu projeto:
    ```sh
    Baixe o arquivo gson-2.8.6.jar e coloque-o no diretório lib/
    Certifique-se de que o arquivo esteja no caminho correto: `lib/gson-2.8.6.jar`
    ```

3. Compile o projeto:
    ```sh
    javac -cp lib/gson-2.8.6.jar -d bin src/WeatherService.java
    ```

## Uso

1. Execute o projeto:
    ```sh
    java -cp bin:lib/gson-2.8.6.jar WeatherService
    ```
    No Windows, use `;` em vez de `:` para separar os caminhos no classpath:
    ```sh
    java -cp bin;lib/gson-2.8.6.jar WeatherService
    ```

2. Digite o nome da cidade quando solicitado (sem caracteres especiais).

    Exemplos:

    São Paulo -> Sao Paulo
    Brasília -> Brasilia
    João Pessoa -> Joao Pessoa

## Exemplo de Saída

```
Digite o nome da cidade: Sao Paulo
Cidade: São Paulo, Temperatura Atual: 17.37°C, Temperatura Máxima: 18.58°C, Temperatura Mínima: 16.75°C, Clima: algumas nuvens
Previsão para os próximos dias:
Data: 08-10-2024, Temperatura Máxima: 17.54°C, Temperatura Mínima: 17.43°C, Clima: nuvens dispersas
Data: 09-10-2024, Temperatura Máxima: 18.59°C, Temperatura Mínima: 18.59°C, Clima: nublado
Data: 10-10-2024, Temperatura Máxima: 18.52°C, Temperatura Mínima: 18.52°C, Clima: chuva leve
Data: 11-10-2024, Temperatura Máxima: 19.08°C, Temperatura Mínima: 19.08°C, Clima: nublado
Data: 12-10-2024, Temperatura Máxima: 21.4°C, Temperatura Mínima: 21.4°C, Clima: chuva leve
...
Alertas Meteorológicos:
Evento: Tempestade, Descrição: Tempestade severa prevista para a região.
```

## Contribuição

1. Faça um fork do projeto.
2. Crie uma nova branch (`git checkout -b feature/nova-funcionalidade`).
3. Faça commit das suas alterações (`git commit -am 'Adiciona nova funcionalidade'`).
4. Faça push para a branch (`git push origin feature/nova-funcionalidade`).
5. Abra um Pull Request.
