# Mapskills - File Manager
Serviço de gerenciamento de arquivos para o projeto mapskills.

Este projeto se trata de um serviço que faz o gerenciamento de arquivos submetidos pelo projeto principal <a href='https://github.com/Marcelo-Inacio/mapskills/tree/master'>Mapskills</a>,
com o objetivo de concentrar em um único ponto as imagens das cenas do projeto.

Este projeto está desenvolvido com <a href='https://projects.spring.io/spring-boot/'>Spring Boot</a>.

----

Executar testes, necessário estar habilitado permissão de escrita/leitura para o diretório disposto no pom.xml de acordo com sistema operacional.
- `mvn test -Plocal` para ambiente windows
- `mvn test -Pazure` para ambiente linux

Executar serviço.
- `mvn spring-boot:run`

----

Buildar projeto e gerar imagem docker.
- `mvn clean package -Pazure dockerfile:build`

----

Criar container a partir da imagem com pasta source compartilhada com host.
- `docker run -v /home/filemanager/source:/home/filemanager/source -ti -d --name filemanager -p 8082:8082 mapskills/filemanager`

----

Listar todos os arquivos contidos no container.
- `docker exec filemanager ls /home/filemanager/source`
