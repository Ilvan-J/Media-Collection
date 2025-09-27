🎬 Coleção De Mídias (Mídia Collection)
Aplicação de adição de mídias. É possível salvar o tipo de mídia, o status da produção, e se você finalizou de acompanhar tudo sobre a mídia. Além também de filtrar suas mídias por status de produção e acompanhamento da mídia.
🛠️ Tecnologias Utilizadas
 *  * 🔑 Requisito de Segurança
Para o correto funcionamento da autenticação e validação do token JWT, a aplicação necessita de chaves RSA (privada e pública) configuradas.
🛣️ Mapeamento de Rotas
O Host padrão para todas as requisições é localhost:8080. A autenticação em rotas protegidas é feita via Cookie (após o login).


Rotas De Usuário

Mapeamento de todas as rotas relacionadas ao gerenciamento de usuários (/api/media-collection/users).

Cadastrar Usuário

| Método | Rota |
|---|---|
| POST | /api/media-collection/users/newUser |
Request
POST /api/media-collection/users/newUser
HOST: localhost: 8080
Content-Type: application/json

``` json
{
  "email": "exemploemail@gmail.com",
  "password": "Sn6Xl#mJ8S5#vs#"
}
```

Response
``` 
statusCode: 201
```

Login de Usuário
| Método | Rota |
|---|---|
| POST | /api/media-collection/token/login |
Request
POST /api/media-collection/token/login
HOST: localhost:8080
Content-Type: application/json

``` json
{
  "email": "exemploemail@gmail.com",
  "senha": "Sn6Xl#mJ8S5#vs#"
}
``` 

Response
> Observação: O ID de usuário necessário para consultas é codificado no token. A autenticação para as próximas rotas será feita via Cookie.
>
```
statusCode: 200
```
``` json
{
  "accessToken": "ajsajbd25#sdvhvhda#hbhh#vgvdhasgdv#vds",
  "expiresIn": 300
}
```

Rotas de Mídias (Protegidas)
Mapeamento de todas as rotas relacionadas às mídias (/api/media-collection/medias).
Salvar uma Mídia
| Método | Rota |
|---|---|
| POST | /api/media-collection/medias/save |
Request
POST /api/media-collection/medias/save
HOST: localhost:8080
Content-Type: application/json
Cookie: {token_de_acesso_no_cookie}

``` json
{
    "name": "Dragon Ball Z",
    "seasons": 5,
    "idTypeMedia": 3,
    "idProductionStatus": 1,
    "idWatchingStatus": 1
}
```

Response
``` 
StatusCode: 201
```
Exibir Todas as Mídias (Filtros e Paginação)
| Método | Rota |
|---|---|
| GET | /api/media-collection/medias/list-all |
| Parâmetro | Tipo | Descrição | Exemplo | Obrigatório |
|---|---|---|---|---|
| typeMedia | Long | Filtra pelo ID do tipo de mídia (ex.: 3 para Anime). | 1 | Não |
| productionStatus | Long | Filtra mídias pelo ID do status de produção. | 1 | Não |
| watchingStatus | Long | Filtra mídias pelo ID do status de visualização. | 1 | Não |
| name | String | Filtra mídias que contenham a palavra fornecida no nome. | Dragon | Não |
| orderBy | String | Campo para ordenação: dateOfAdded ou modificationDate. | dateOfAdded | Não |
| orderDirection | String | Direção da ordenação: asc ou desc. | desc | Não |
| page | int | Número da página para paginação. | 0 | Não |
| pageSize | int | Quantidade de itens por página. | 10 | Não |
Request
GET /api/media-collection/medias/list-all?typeMedia=1&productionStatus=2&orderBy=modificationDate&orderDirection=asc&page=0&pageSize=10
HOST: localhost:8080
Content-Type: application/json
Cookie: {token_de_acesso_no_cookie}

Response
``` 
StatusCode: 200
```

``` json

{
    "content": [
        {
            "idMedia": "20d17bf4-f56d-4332-9491-b3cbfe17909e",
            "name": "Dragon Ball Z",
            "seasons": 5,
            "nameTypeMedia": "Anime",
            "nameProductionStatus": "Finalizado",
            "nameWatchingStatus": "Assistido",
            "userEmail": "admin@gmail.com",
            "dateOfAdded": "2025-03-14T10:36:00.949725",
            "modificationDate": "2025-03-14T10:36:00.949725"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "empty": false,
            "sorted": true,
            "unsorted": false
        },
        "offset": 0,
        "unpaged": false,
        "paged": true
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 1,
    "size": 10,
    "number": 0,
    "sort": {
        "empty": false,
        "sorted": true,
        "unsorted": false
    },
    "first": true,
    "numberOfElements": 1,
    "empty": false
}
```

Exibir Detalhes de uma Mídia
| Método | Rota |
|---|---|
| GET | /api/media-collection/medias/details/{id_media} |
Request
GET /api/media-collection/medias/details/{id_media}
HOST: localhost:8080
Content-Type: application/json
Cookie: {token_de_acesso_no_cookie}

Response
```
StatusCode: 200
```

``` json
{
    "idMedia": "20d17bf4-f56d-4332-9491-b3cbfe17909e",
    "name": "Dragon Ball Z",
    "seasons": 5,
    "nameTypeMedia": "Anime",
    "nameProductionStatus": "Finalizado",
    "nameWatchingStatus": "Assistido",
    "userEmail": "admin@gmail.com",
    "dateOfAdded": "2025-03-14T10:36:00.949725",
    "modificationDate": "2025-03-14T10:36:00.949725"
}
```

Atualizar uma Mídia
| Método | Rota |
|---|---|
| PUT | /api/media-collection/medias/update/{id_media} |
Request
PUT /api/media-collection/medias/update/{id_media}
HOST: localhost:8080
Content-Type: application/json
Cookie: {token_de_acesso_no_cookie}

``` json
{
    "name": "Dragon Ball Z",
    "seasons": 5,
    "idTypeMedia": 3,
    "idProductionStatus": 1,
    "idWatchingStatus": 1
}
```

Response
``` 
StatusCode: 200
```
Apagar uma Mídia
| Método | Rota |
|---|---|
| DELETE | /api/media-collection/medias/delete/{id_midia} |
Request
DELETE /api/media-collection/medias/delete/{id_midia}
HOST: localhost:8080
Content-Type: application/json
Cookie: {token_de_acesso_no_cookie}

Response

```
StatusCode: 204
```

Rotas de Buscas Auxiliares (Protegidas)
Exibir Todos os Tipos de Mídia
Mapeamento: /api/media-collection/type-media
| Método | Rota |
|---|---|
| GET | /api/media-collection/type-media/getAll |

Request

GET /api/media-collection/type-media/getAll?page=0&size=10&sort=name,ASC
HOST: localhost:8080
Content-Type: application/json
Cookie: {token_de_acesso_no_cookie}

Response

``` 
StatusCode: 200
```

Exibir Todos os Status de Acompanhamento (Watching Status)
Mapeamento: /api/media-collection/watching-status
| Método | Rota |
|---|---|
| GET | /api/media-collection/watching-status/getAll |
Request
GET /api/media-collection/watching-status/getAll?page=0&size=10&sort=name,ASC
HOST: localhost:8080
Content-Type: application/json
Cookie: {token_de_acesso_no_cookie}

Response
``` 
StatusCode: 200
```

Exibir Todos os Status de Produção (Production Status)
Mapeamento: /api/media-collection/production-status
| Método | Rota |
|---|---|
| GET | /api/media-collection/production-status/getAll |
Request
GET /api/media-collection/production-status/getAll?page=0&size=10&sort=name,ASC
HOST: localhost:8080
Content-Type: application/json
Cookie: {token_de_acesso_no_cookie}

Response
``` 
StatusCode: 200
```