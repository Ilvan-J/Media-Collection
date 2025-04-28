# Coleção De Mídias

Aplicação de adição de mídias. É possível salvar o tipo de mídia o status da produção, e se você finalizou de acompanhar tudo sobrea mídia. Além também de filtrar suas mídias 
por stutus de produção e acompanhamento da mídia.

# Tecnologias utilizadas
* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
* ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) 

# Mapeamento de rotas
# Rotas De Usuário
Mapeamento de todas as rotas relacionadas ao gerenciamento de usuários

## Cadastrar usuário

### Request

~~~http
POST /api/media-collection/users/newUser
HOST: localhost: 8080
Content-Type: application/json
~~~

~~~json
{
  "email": "exemploemail@gmail.com",
  "password": "Sn6Xl#mJ8S5#vs#"
}
~~~

### Response 

~~~http
statusCode: 201
~~~

## Login de usuário

### Request

~~~http
POST /api/media-collection/login
HOST: localhost:8080
Content-Type: application/json
~~~

~~~json 
{
  "email": "exemploemail@gmail.com",
  "senha": "Sn6Xl#mJ8S5#vs#"
}
~~~

### Response 
### observação id de usuário necessário para consultas é codificado no token

~~~http 
statusCode: 200
~~~

~~~json
{
  "accessToken": "ajsajbd25#sdvhvhda#hbhh#vgvdhasgdv#vds",
  "expiresIn": 300
}
~~~

# Rotas de mídias
Mapeamento de todas as rotas relacionadas as mídias

## Salvar uma mídia

### Request

~~~ http
POST / api/media-collection/medias/save
HOST: localhost:8080
Content-Type: application/json
Authorization: Bearer {token_de_acesso}
~~~

~~~ json
{
    "name": "Dragon Ball Z",
    "seasons": 5,
    "idTypeMedia": 3,
    "idProductionStatus": 1,
    "idWatchingStatus": 1
}
 ~~~

### Response
~~~http
StatusCode: 201
~~~

## Exibir todas as mídias

| Parâmentro | tipo | Descrição | Exemplo | Obrigatório|
|------------|------|-----------|---------|------------|
| `typeMedia`  | `Long` | Filtra pelo ID do tipo de mídia(ex.: `3` para Anime, `2` para série) | `1` | Não
| `productionStatus` | `Long` | Filtra mídias pelo ID do status de produção (ex.: `1` Em Produção, `2` para Finalizado) | `1` | Não
| `watchingStatus` | `Long` | Filtra mídias pelo ID do status de visualização (ex.: `1` para Assistindo, `2` para Concluído) | `1` | Não
| `name` | `String` | Filtra mídias que contenham a palavra fornecida no nome. | `Dragon` | Não
| `orderBy` | `String` | Define o campo para ordenação. Valores possíveis: `dateOfAdded` ou `modificationDate`. | `dateOfAdded` | Não
| `orderDirection` | `String` | Direção da ordenação. Valores possíveis: `asc` (crescente) ou `desc` (decrescente). | `desc` | Não
| `page` | `int` | Número da página para paginação. | `0` | Não
| `pageSize` | `int` | Quantidade de itens por página. | `10` | Não

### Request

~~~http
GET / api/colecao-de-midias/medias/list-all?typeMedia=1&productionStatus=2&orderBy=modificationDate&orderDirection=asc&page=0&pageSize=10
HOST: localhost:8080
Content-Type: application/json
Authorization: Bearer {token_de_acesso}
~~~

### Response 

~~~http
StatusCode: 200
~~~

~~~json
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
~~~

## Exibir detalhes de uma mídia


### Parâmetros opcionais

### Request
=

``` http
GET / api/colecao-de-midias/medias/details/{id}
HOST: localhost:8080
Content-Type: application/json
Authorization: Bearer {token_de_acesso}
```

### Response

~~~http
StatusCode: 200
~~~

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

## Atualizar uma mídia

### Request

~~~http
PUT /api/media-collection/medias/update/{id_media}
HOST: localhost:8080
Content-Type: application/json
Authorization: Bearer {token_de_acesso}
~~~

~~~json
{
    "name": "Dragon Ball Z",
    "seasons": 5,
    "idTypeMedia": 3,
    "idProductionStatus": 1,
    "idWatchingStatus": 1
}
~~~

### Response
~~~http
StatusCode: 200
~~~

## Atualizar uma mídia
~~~http
UPDATE /api/median-collection/medias/update/{id_media}
HOST: localhost:8080
Content-Type: application/json
Authorization: Bearer {token_de_acesso}
~~~

~~~json 
{
    "name": "Dragon Ball Z",
    "seasons": 5,
    "idTypeMedia": 3,
    "idProductionStatus": 1,
    "idWatchingStatus": 1
}
~~~

## Apagar uma mídia

### Request

~~~http 
DELETE / api/median-collection/medias/delete/{id_midia}
HOST: localhost:8080
Content-Type: application/json
Authorization: Bearer {token_de_acesso}
~~~

### Response

~~~http
StatusCode: 204
~~~
