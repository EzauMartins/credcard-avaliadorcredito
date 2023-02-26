  Olá, esse repositorio foi criado para registrar meu desenvolvimento durante um treinamento de microserviços, mensaggeria e docker.
se trata de um sistema simples de criação de cartão e cliente, sendo possivel avaliar se o cliente está apto ou não
para adiquirir um cartão dependendo da sua faixa salaria e idade, no entanto esse não foi o foco principal mas sim a
noção de microserviços,rabbitMQ e docker. <br>

Tecnologias/Ferramentas:<br>
DOCKER<br>
SPRINGBOOT<br>
INTELLIJ<br>
KEYCLOAK(18.0.0)<br>
RABBITMQ(3.11)<br>
INSOMINIA<br>

rodar o keycloak e rabbitmq no docker.<br>

no estado atual é possivel gerar imagens dos microserviços a partir dos dockerfile, observar os paramentros a serem passados ae rodar.<br>

nescessario criar um network docker<br>

Para rodar o projeto é nescessario ter o rabbitmq rodando pois o avaliador de credito nescessita da queue (emissao-cartoes).<br>
KEYCLOAK tambem nescessario para gerar tokens de acesso para execução de get/post e afins.<br>

ABAIXO os repositorios dos microserviços desenvolvidos e ferrametas de cloud e gateway.<br>

REPOSITORIOS<br>
-><a href="https://github.com/EzauMartins/CredCard-eurekaServer/" target="_blank">EUREKASERVE</a>  
-><a href="https://github.com/EzauMartins/CredCard-CloudGateway/" target="_blank">GETEWAY</a>   
-><a href="https://github.com/EzauMartins/CredCard-Card" target="_blank">MICROSERVICE CARTÃO</a>  
-><a href="https://github.com/EzauMartins/CredCard-clients-servics" target="_blank">MICROSERVICE CLIENTE</a>   
-><a href="https://github.com/EzauMartins/credcard-avaliadorcredito" target="_blank">MICROSERVICE AVALIADOR DE CREDITO</a>  ATUAL  

(README EM EDIÇÂO)
