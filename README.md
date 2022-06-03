# fluig-public-dataset

Exemplo de como criar um componente que permite acesso público a um determinado dataset sem expor o usuário e senha do fluig.

Criado com base no sample-component (https://git.fluig.com/projects/SAMPLES/repos/projetos/browse/sample-component)

* **public-dataset-pack**: projeto responsável por empacotar todo o public-dataset em um arquivo do tipo .ear.

	O package do projeto deve ser do tipo **.EAR** (Enterprise Application Archive).
	Para essa configuração, no arquivo public-dataset/public-dataset-pack/pom.xml a tag <packing> deve estar assim: **<packaging>ear</packaging>**.
---


* **public-dataset-config**: projeto onde estão os arquivos de configuração do componente:

	Para que o componente seja instalado corretamente, é necessário o arquivo **component.xml**.
	Esse arquivo se encontra em public-dataset-config/src/main/resources/component.xml.
	É obrigatório que o **component code** seja único: **component code="public_dataset_app"**.
---


* **public-dataset-rest**: projeto responsável em disponibilizar as API Rest para os Services das tabelas do app

	
	Para testar o Rest SEM autenticação, acesse o endpoint:
	
		- FLUIG_URL/publicdataset/api/v1/{tenantId}/aws/{instanceCode}
---	


Para empacotar o projeto e gerar o arquivo **EAR**, será necessário utilizar o Maven. Entre com o seguinte comando na raiz do projeto:

     - mvn clean install
     - Você pode também utilzar o Eclipse for Java EE Developers para executar o comando mvn install.

Ao gerar o pacote, o próximo passo é fazer o upload através da Central de Componentes. Após enviar o .EAR, será necessário ativar o componente. Procure pelo código, que está no component.xml, faça a ativação, atualize a página(F5) e acesse as páginas criadas. (Essa etapa é necessária apenas no desenvolvimento do app).


Para acessar o rest é necessário criar um OAuth Provider e um OAuth App bem como um usuário integrador no cadastro do OAuth.

Também é necessário criar o dataset aws-instances que receba a constraint INSTANCE_CODE