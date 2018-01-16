Projecto final de Computação Móvel - Ler Guardar Notícias
---------------------------------------------------------

<b>Resumo</b>:
A app trata-se de um leitor das últimas notícias fornecidas pela API Hacker-News lendo para esse efeito um objecto JSON.
Por defeito vai buscar as últimas 5 notícias, podendo o utilizador especificar o nº que deseja.

------------------------------------------------------------------------------------------------------------------

<b>Funcionamento</b>:
- Assim que a lista é carregada a mesma é guardada em "cache" numa Base de Dados SQLite, podendo ser devolvida instantaneamente essa mesma
lista da próxima vez que se iniciar a app, não limpando assim as notícias já carregadas anteriormente.
- Se carregar nova lista de notícias a Base de Dados é limpa, seguido de nova escrita com a lista actualizada.
- Ao abrir uma das notícias é carregado uma nova activity com WebView que permite visualizar a mesma.

---------------------------------------------------------

<b>Bugs conhecidos</b>:
- Por alguns problemas do tipo de notícias que são carregadas por vezes a lista pode demorar mais tempo a carregar ou simplesmente não
carrega. Dar ordem para carregar apenas 1 ou 2 notícias pode ajudar, se evitar a notícia que causa o problema.
- Outro problema passível de ocorrer prende-se com o erro "TransactionTooLargeException", quando a notícia redireciona para site que satura
o transaction buffer do Binder. O problema poderia ser resolvido com um "Parcel" ou simplesmente não usar WebView e abrir prompt para abrir
com browser de Internet externo (como por exemplo Chrome etc), mas devido à sua baixa frequência e pelo objectivo final já estar cumprido,
manteve-se o modo de funcionamento actual.
