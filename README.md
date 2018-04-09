### MAP SKILLS - FILEMANAGER - FACULDADE DE TECNOLOGIA PROF º JESSEN VIDAL

##### Projeto de gerenciamento de arquivos para o projeto mapskills-engine.

Este projeto se trata de um serviço que faz o gerenciamento de arquivos submetidos pelo projeto principal <a href='https://github.com/Marcelo-Inacio/mapskills/tree/master'>Mapskills</a>,
com o objetivo de concentrar em um único ponto as imagens das cenas do projeto.

Este projeto está desenvolvido com <a href='https://projects.spring.io/spring-boot/'>Spring Boot</a>.

Executar testes, necessário estar habilitado permissão de escrita/leitura para o diretório disposto no pom.xml de acordo com sistema operacional (consultar diretorios para criação).
- `mvn test -Plocal` para ambiente windows
- `mvn test -Pazure` para ambiente linux

Executar serviço.
- `mvn spring-boot:run -Plocal`

Buildar projeto e gerar imagem docker.
- `mvn clean package -Pazure dockerfile:build`

Docker : [Docker](https://www.docker.com/ "Docker")
- Construir imagem a partir do Dockerfile  
`docker build -t mapskills/mapskills-filemanager .`

Criar container a partir da imagem com pasta source compartilhada com host.
- `docker run -it -d --rm --network mapskills-network -v /home/filemanager/source:/home/filemanager/source --name mapskills-filemanager -p 8082:8082 mapskills/mapskills-filemanager`

Listar todos os arquivos gerenciados contidos no container.
- `docker exec filemanager ls /home/filemanager/source`
