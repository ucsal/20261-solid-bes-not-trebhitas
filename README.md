Essa é a versão escrita sobre as melhorias feitas no projeto olimpiadas,com o objetivo de seguir as boas práticas e os principios do termo
SOLID. Inicialmente foi observado a necessidade de "repartir" funcionalidades que haviam em App, afim de criar um fluxo onde cada classe
possui responsabilidades de acordo com o objetivo inicial de sua criação e também para eliminar o dominio integral que o App tinha de forma 
desorganizada e perigosa. 
Com isso, meu objetivo principal foi manter o App com as funções que o main traz consigo que seria: executar o programa, chamando todas as 
funções que existem para o codigo funcionar com gostariamos.Dessa forma, foi realizada a separação de responsabilidades,criando um fluxo onde
cada classe passou a exercer um papel específico de acordo com seu propósito,reduzindo o acúmulo de responsabilidades e tornando o sistema 
estruturado.
Assim, o principio da responsabilidade única (SRP) foi aplicado ao dividir o sistema em camadas, onde cada classe passou a ter uma responsabilidade
específica:

- A adição da classe Menu que ficou responsável pela interação inicial com o usuário
- Foi criado um pacote "Repositorio" para alocar as classes "Repository" que implementei, que possuem a 
função de ficar responsável pelo armazenamento dos dados
- Foi criado um pacote "Service" que contém as classes "Service" que implementei com o objetivo dessas classes
  terem a função de manter as regras principais para o funcionamento do sistema e ter uma troca com os metodos
  que rodam as funcionalidades impostas na lista do Menu
- Criei um pacote "Model" o qual, guarda as classes "Participante","Prova","Questão" e "Tentativa" que determina
  o padrão dos objetos e posteriomente quando inicializado, permite que todos os objetos de determinada classe
  possuam as mesmas caracteristicas
- Por fim, a refatoração da classe App que agora possui o papel de chamar as classes com os metodos que precisam
  rodar

  O princípio da inversão de dependência (DIP)
  foi aplicado ao fazer com que as classes de serviço dependam de abstrações de acesso a dados (repositórios), em vez de
  manipular diretamente estruturas como listas.
  Os repositórios são injetados nos services por meio do construtor,facilitando futuras modificações.

  O princípio aberto/fechado (OCP)
  Aplique esse principio ao deixar que os métodos não possuam espaço para mudanças, com a preocupação de colocar em risco o funcionamento do que ja
  existe. Por isso, Por isso, apenas criando novos serviços ou classes para que novas funcionalidades sejam adicionadas sem a necessidade de 
  modificar estruturas já existentes.
