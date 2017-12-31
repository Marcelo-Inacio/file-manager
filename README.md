# File Manager
Serviço de gerenciamento de arquivos para o projeto mapskills

Este projeto se trata de um serviço que faz o gerenciamento de arquivos submetidos pelo projeto principal <a href='https://github.com/Marcelo-Inacio/mapskills/tree/master'>Mapskills</a>,
com o objetivo de concentrar em um único ponto as imagens das cenas do projeto.

Este projeto esta desenvolvido com <a href='https://projects.spring.io/spring-boot/'>Spring Boot</a>.

----

Executar serviço
- `mvn spring-boot:run`

----

Buildar projeto e gerar imagem docker
- `mvn clean package -Pazure dockerfile:build`
