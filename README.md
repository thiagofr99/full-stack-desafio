# Full Stack Challenge (Back-End) - Dictionary Application
Desafio para aplicação de vaga Full Stack.

## Sobre o projeto:
Aplicação WEB RESTFul, com Java 11 utilizando Springboot, projeto com Implementação de Migrations e autenticação JWT.

## 🛠️ Construído com

<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original-wordmark.svg" width="40" height="40"/> Java 11. 

<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original-wordmark.svg" width="40" height="40"/> Spring boot (JPA, Security e MVC). 

<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original-wordmark.svg" width="40" height="40"/> Banco de dados MYSQL. 

Flyway Migrations para versionamento e implementação automatizada do banco de dados.
SonarQube para qualidade do software e cobertura dos testes unitários.

<img src="https://avatars.githubusercontent.com/u/874086?s=280&v=4" width="40" height="40"/> J-Unit para testes unitários

<img src="https://img.icons8.com/color/344/docker.png" width="40" height="40"/> Docker 

<img src="https://img.icons8.com/color/344/travis-ci.png" width="40" height="40"/> Travis CI para integração continua.

### 📋 Pré-requisitos

Instalar o Docker através dos links abaixo:

Para Windows: 
```
https://docs.docker.com/desktop/windows/install/
```

Para Mac: 
```
https://docs.docker.com/desktop/mac/install/
```

Para Unbuntu:
```
https://docs.docker.com/engine/install/ubuntu/
```

Instalar PowerShell para Windows ou Mac
```
https://docs.microsoft.com/pt-br/powershell/scripting/install/installing-powershell?view=powershell-7.2
```

### 🔧 Instalação

Após os pré requisitos instalados deve-se:

Abrir o Power Shell na pasta do projeto onde se encontra o arquivo docker-compose.yml.

![image](https://user-images.githubusercontent.com/72176866/171308392-112a9c75-4779-4441-8a07-f363f034ea9a.png)

![image](https://user-images.githubusercontent.com/72176866/171308465-706edd40-e02d-454a-91fe-d08bf1f1bbb1.png)

![image](https://user-images.githubusercontent.com/72176866/171308540-f6fd18df-1a69-4eec-bff4-66b27298e98b.png)

Execute no PowerShell o comando para criar a imagem:
```
docker-compose build
```

Após todo o processo de building execute o comando subir a imagem:

```
docker-compose up
```

Verifique se esta tudo ok executando no navegar: 

```
localhost:8080/
```

Deve aparecer a seguinte mensagem.
![image](https://user-images.githubusercontent.com/72176866/171308914-e5a8548b-b997-4f1b-8ec2-9be248b3332d.png)

Você também pode consultar toda a documentação do Open API através:
```
localhost:8080/swagger-ui.html
```

A seguinte tela deve abrir:
![image](https://user-images.githubusercontent.com/72176866/171309009-bc16ed02-a0b1-440b-a552-a4f9acf9ece2.png)


## ✒️ Autor
**Thiago Furtado Rodrigues**

## 🎁 Expressões de gratidão
Agradeço a @Coodesh pela oportunidade de desenvolver e aplicar novos conhecimentos nesse desafio o qual me proporcionou novas vivências e aprendizados.

> This is a challenge by <a href="https://coodesh.com">Coodesh</a> 
