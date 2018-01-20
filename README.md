Projecto final de Computação Móvel - Ler Guardar Notícias
---------------------------------------------------------

<b>Resumo</b>:
A app trata-se de um leitor das últimas notícias fornecidas pela API Hacker-News lendo para esse efeito um objecto JSON.
Por defeito vai buscar as últimas 5 notícias (e/ou até a um limite máximo igual ao nº de notícias válidas obtidas), podendo o utilizador especificar o nº que deseja.

------------------------------------------------------------------------------------------------------------------

<b>Funcionamento</b>:
- Assim que a lista é carregada a mesma é guardada em "cache" numa Base de Dados SQLite, podendo ser devolvida instantaneamente essa mesma
lista da próxima vez que se iniciar a app, não limpando assim as notícias já carregadas anteriormente.
- A lista guardada na BD é carregada por ordem do utilizador com o botão "ABRIR CACHE OFFLINE".
- Se carregar nova lista de notícias a Base de Dados é limpa, seguido de nova escrita com a lista actualizada.
- Ao abrir uma das notícias é carregado uma nova activity com WebView que permite visualizar a mesma.

---------------------------------------------------------

<b>Limitações/Bugs conhecidos</b>:
- Devido a problems com alguns tipos de notícias carregadas por vezes a lista pode demorar mais tempo a carregar ou simplesmente não
carrega. Dar a ordem de carregar apenas 1 ou 2 notícias pode ajudar, especialmente se evitar o carregamento da notícia que origina o problema.
- Outro problema passível de ocorrer prende-se com o erro "TransactionTooLargeException", quando a notícia redireciona para site que satura o transaction buffer do Binder. O problema poderia ser resolvido com um "Parcel" ou simplesmente não usar WebView e abrir prompt para abrir com browser de Internet externo (como por exemplo Chrome etc), mas devido à sua baixa frequência e pelo objectivo final já estar cumprido, manteve-se o modo de funcionamento actual.
- Finalmente ocorreu somente na rede WiFi "eduroam" em que apenas 1 notícia era carregada, independentemente do nº especificado. Comprovado comportamente diferente com Dados móveis ou redes WiFi residenciais. Possivelmente algum bloqueio IP que devolvesse um valor nulo e cancelasse carregamento de notícias posteriores.
