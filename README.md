# divulga_potiguar

<div align="center">
  <img src="https://github.com/netox64/b_divulgapotiguar/blob/main/docs/api_print1.png" width="250" height="250" />
  <img src="https://github.com/netox64/b_divulgapotiguar/blob/main/docs/api_print2.png" width="250" height="250" />
</div>

<h4 align="center">Este projeto é um backend com lógica relacionada à promoção de imóveis que estão à venda na região designada.</h4>
<p align="center">
    <a href="#Tecnologias_Utilizadas">Tecnologias Utilizadas</a> •
    <a href="#Arquitetura_de_Pastas">Arquitetura de Pastas</a> •
    <a href="#Executando_Aplicativo">Executando Aplicativo</a> •
    <a href="#Estrutura_do_Banco">Estrutura do Banco</a> •
    <a href="#About_the_Author">Sobre o Autor</a> •
    <a href="https://github.com/netox64/b_divulgapotiguar/blob/main/LICENSE">Licenciamento</a>
</p>

## Tecnologias_Utilizadas

- As seguintes tecnologias foram usadas neste projeto:
  - Java como linguagem de programação.
  - Springboot, o framework para backend.
  - Ferramenta de conteinerização docker.
  - Tesseract, que é um sistema de reconhecimento ótico de caracteres (OCR) de código aberto. Ele é usado para converter imagens contendo texto em texto editável, permitindo que o computador "leia" o que está impresso ou escrito em documentos digitalizados, imagens ou fotos. Em especial nesta aplicação ele vai ser utilizado para ler imagem no documento pdf, que pode ser um pdf com texto simples ou uma imagem ai entra o Tesseract.

## Arquitetura_de_Pastas
```
api.src
  ├  └─  main.java.ofc.api
  ├                    └──────────  config : com todas as configurações do projeto, springSecurity, Swagger, Listeners ...
  ├                    └──────────  controllers : com todas as interfaces, genericos, implementação de controladores...
  ├                    └──────────  entities  : com modelos da aplicação.
  ├                    └──────────  repositories :com todos os tipos genericos e interfaces repositories que extendem de JpaRepository...
  ├                    └──────────  services : com todas as interfaces de serviço, genericos e implementações ...
  ├                    └──────────  utils : com todos os dtos, classes auxiliares, como validadores etc ...
  ├  
  └───────  main.resources
  ├                    └──────────  tessdata                   : pasta com todos arquivos de treinamento do tesseract
  ├                    └──────────  aplication.properties      - configurações
  ├                    └──────────  aplication-dev.properties  - configurações de ambiente de desenvolvimento/produção
  ├                    └──────────  aplication-test.properties - configurações de ambiente de teste
  ├                    └──────────  sonar-project.properties   - configuração reconhecimento sonar cloud
  ├  
  └───────  test
  ├──  docher-compose.yml
  └──  pom.xml : all dependecies
```

## Estrutura_do_Banco

<img src="https://github.com/netox64/b_divulgapotiguar/blob/main/docs/api_potiguar.png" />


## Prerequisitos

- SDKMAN para a versão do gerenciador jdk, eu uso mas se não quizer instala só a JDK 23 mesmo
- Docker e plugin Docke-compose
- Tenha o tesseract instalado na sua máquina

```
  sudo apt-get install tesseract-ocr
```
- Precisamos exportar um caminho para o tesseract-ocr reconhecer o caminho e executar:

```
  export TESSDATA_PREFIX=/home/cl10/Projetos/b_divulgapotiguar/api/src/main/resources/tessdata/
```

- Dar permissões na pasta:

```
  sudo chmod -R 755 /home/cl10/Projetos/b_divulgapotiguar/api/src/main/resources/tessdata/
```
- Em caso de erro:
> [!WARNING]
> temos que dar permissão a 2 arquivos importantes para funcionamento do Tesseract
```
  sudo chmod 644 /home/cl10/Projetos/b_divulgapotiguar/api/src/main/resources/tessdata/configs &&
  sudo chmod 644 /home/cl10/Projetos/b_divulgapotiguar/api/src/main/resources/tessdata/pdf.ttf
```


## Executando_Aplicativo
- crie um arquivo chamado *application-dev.properties* dentro da pasta resources
- defina um jwt com base 64 caracteres, e seu e-mail e senha do Zoho. E finalmente um segredo
- Você pode usar este exemplo se quiser, só precisa do seu e-mail e senha do Zoho.

```
  jwt=secrets_com64_caracteres
  username=example@zohomail.com
  password=Example34#
  token_secret=um_toquen_qualquer
  cpf_secret=cpf_divulga_secrets
  
  client_id=Client_Id_...
  client_secret=Client_Secret_...
  certificate=./certs/suachavep12
```

- crie o banco::
 ```
    cd api && docker-compose up
 ```

- instale as dependencias:
 ```
  sudo apt install maven -y &&
  mvn install 

 ```

- execute:
```
  java -jar target/nome_projeto-1.0-SNAPSHOT.jar 
  or
  mvn spring-boot:run
  
```
- simplificando:
```
  docker-compose up -d && mvn spring-boot:run #or
  docker-compose start -d && mvn spring-boot:run
```

- http://localhost:8080/swagger-ui/index.html?urls.primaryName=public

> [!IMPORTANT]
> Se você não conseguiu executar corretamente, vai no meu linkedin que tem demonstração dele.

> [!WARNING]
> Ao fazer solicitações, fique atento aos tokens e às políticas de autorização do aplicativo. Erros também podem ser encontrados, pois ele ainda está em desenvolvimento.


## About_the_Author
- Clodoaldo Neto :call_me_hand:
