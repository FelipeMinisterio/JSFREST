


A solução limpa e com melhor fluxo foi o uso do JAX-RS para fazer os serviços REST da aplicação com o uso de JSF para o front junto com o Jersey. 
Fiz o uso do Jackson tambem para transformar Objetos javas em JSON e vice versa para facilitar a comunicação entre servidor/cliente.
A utilização de MultiPart foi fundamental para a inserção e o download dos arquivos de PDF
O funcionamento do projeto deve-se a estrutura montada:

Model -> Service -> Resources -> Controller.

Service <- Controller <- View
		              
						