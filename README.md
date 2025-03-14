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
POST /api-colecao-de-midias/usuarios/cadastrar-usuario
HOST: localhost: 8080
Content-Type: application/json
~~~

~~~json
{
  "email": "exemploemail@gmail.com",
  "senha": "Sn6Xl#mJ8S5#vs#"
}
~~~

### Response 

~~~http
statusCode: 201
~~~

~~~json
{
  "email": "exemploemail@gmail.com"
}
~~~

## Login de usuário

### Request

~~~http
POST /api-colocao-de-midias/usuarios/login-de-usuario
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
statusCode: 203
~~~

~~~json
{
  "token": "ajsajbd25#sdvhvhda#hbhh#vgvdhasgdv#vds"
}
~~~

# Rotas de mídias
Mapeamento de todas as rotas relacionadas as mídias

## Salvar uma mídia

### Request

~~~ http
POST / api/colecao-de-midias/midias/salvar-midia
HOST: localhost:8080
Content-Type: application/json
Header: Bearer {token_de_acesso}
~~~

~~~ json
{
  "nomeDaMidia":  "Dragon Ball Z",
  "tipoDeMidiaId":  1,
  "temporadas":  5,
  "statusProducaoId":  1,
  "statusAcompanhamentoId":  1
 }
 ~~~

### Response
~~~http
StatusCode: 201
~~~
~~~ json
{
  "nomeDaMidia":  "Dragon Ball Z",
  "tipoDeMidiaId":  1,
  "temporadas":  5,
  "statusProducaoId":  1,
  "statusAcompanhamentoId":  1,
  "dataDaAdicao":  "22-12-2024",
  "dataDeModificacao":  "22-12-2024"
}
~~~

## Exibir todas as mídias

### Request

~~~http
GET / api/colecao-de-midias/midias/listar-midias
HOST: localhost:8080
Content-Type: application/json
Header: Bearer {token_de_acesso}
statusDeAcompanhamento: {opcional, ex: "finalizado"}?
ordemDeAdicao: {opcional, ex; "ASC"}

~~~

### Response 

~~~http
StatusCode: 200
~~~

~~~json
[
  {
   "nomeDaMidia":  "Dragon Ball Z",
   "tipoDeMidia":  "Anime",
   "temporadas":  5,
   "statusProducao":  "Finalizado",
   "statusDeAcompanhamento":  "Finalizado",
   "dataDaAdicao":  "22-12-2024",
   "dataDeModificacao":  "22-12-2024"
  }
]
~~~

## Exibir detalhes de uma mídia

### Request

``` http
GET / api/colecao-de-midias/midias/detalhes-midia/{id}
HOST: localhost:8080
Content-Type: application/json
Header: Bearer {token_de_acesso}
```

### Response

~~~http
StatusCode: 200
~~~

 ``` json
  {
    "id": 1,
    "nome":  "Dragon Ball Z",
    "tipoDeMidia":  "Anime",
    "temporadas":  5,
    "statusDaProducao":  "Finalizado",
    "statusDeAcompanhamento":  "Finalizado",
    "dataDaAdicao":  "22-12-2024",
    "dataDeModificacao":  "22-12-2024"
  }
```

## Atualizar uma mídia

### Request

~~~http
PUT /api/colecao-de-midias/midias/atualizar-midia/{id_midia}
HOST: localhost:8080
Content-Type: application/json
Header: Bearer {token_de_acesso}
~~~

~~~json
 {
   "nomeDaMidia":  "Dragon Ball Z",
   "tipoDeMidiaId":  1,
   "temporadas":  5,
   "statusProducaoId":  1,
   "statusAcompanhamentoId":  1
 }
~~~

### Response
~~~http
StatusCode: 200
~~~
~~~ json
{
  "nomeDaMidia":  "Dragon Ball Z",
  "tipoDeMidiaId":  1,
  "temporadas":  5,
  "statusProducaoId":  1,
  "statusAcompanhamentoId":  1,
  "dataDaAdicao":  "22-12-2024",
  "dataDeModificacao":  "23-12-2024"
}
~~~

## Apagar uma mídia

### Request

~~~http 
DELETE / api/colecao-de-midias/midias/apagar-midia/{id_midia}
HOST: localhost:8080
Content-Type: application/json
Header: Bearer {token_de_acesso}
~~~

### Response

~~~http
StatusCode: 204
~~~

~~~json
{
  "messagem": "Mídia apagada com sucesso"
}
~~~