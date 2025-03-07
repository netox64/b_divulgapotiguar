## Requisitos base para sistema

 - [x] Documentação Swagger

### Sistema de usuarios
- É um dos principais agentes de funcionalidade em qualquer tipo de sistema, e na maioria dos deles temos que fazer:
    - [x] O sistema deve permitir cadastro de usuarios.
    - [x] O sistema deve ter a função de mensageria via email.
    - [x] O sistema deve permitir **redefinição de senhas** de usuarios.
    - [x] O usuario deve ser capaz de authenticar com conta do google.
    - [x] O sistema deve permitir modificação de tipo de usuario.
    - [x] O usuario deve ser capaz de visualizar suas mensagens de notificações.

### Sistema de Feedbacks
- É bem comum vermos nos sistemas web e mobile:
    - [x] 1 feedback deve ser atrelado a um **usuario**, assim como a um **anuncio** e ele deve ser escrito além de dado opinião visual via stars.
    - [x] Apenas o usuario criador do feedback deve ser capaz de **excluir, editar** etc...

### Sistema de planos
- É uma importante forma de monetizar algo em aplicações.
    - [x] 1 plano pode conter varios espaços de divulgação por mês.
    - [x] 1 plano deve ser liberado ao usuario, quando for realizado o pagamento.
    - [x] Ele deve definir quantas postagens de anuncios o usuario pode realizar.

### Sistema de pagamentos
- É a mais importante forma de arrecadação nos sistemas e deve receber  uma atenção quanto a segurança.
    - [ ] 1

### Sistema de Anuncios
- É a principal funcionalidade do sistema.
    - [ ] deve ser verificado se o usuario possui anuncio disponivel antes dele iniciar a criação.
    - [ ] cada anunciodeve ter pelo menos 3 fotos do imovel.
    - [ ] cada ununcio deve ser categorizado.

### Sistema de imoveis
- É o objeto em destaque que deve ser cuidado pelo sistema.
    - [ ] cada imovel deve pertencer a um usuario, somente.
    - [ ] um imovel deve ser retirado dos anuncios assim que qualificado ou denunciado como vendido.

#### Diferencial das outras plataformas
 Diferenciais para sua plataforma de imóveis
1️⃣ Realidade Aumentada e Tours 3D     -- impossivel, tem Three js pra mostrar no front, mas java é lento pra ficar transitando dados grandes, mas seria inviavel

Permita que os usuários visualizem o imóvel em 360° ou até usem realidade aumentada para ver como ficariam os móveis no espaço.
Diferencial: Experiência mais imersiva, ajudando na decisão de compra.
2️⃣ Chat com IA e Respostas Automáticas -- Não faz sentido

Chatbot com IA para responder dúvidas sobre imóveis, agendar visitas e enviar alertas personalizados.
Diferencial: Redução no tempo de resposta e aumento da eficiência das negociações.
3️⃣ Geolocalização Inteligente          -- Legal

Sugira imóveis com base na localização do usuário e filtros como bairros seguros, proximidade de escolas e transporte.
Diferencial: Facilidade para encontrar o imóvel ideal sem precisar buscar manualmente.
4️⃣ Match Inteligente (Estilo Tinder)   -- Legal

Algoritmo que sugere imóveis baseados nas preferências do usuário.
O usuário pode curtir ou rejeitar anúncios, e se houver interesse de ambos os lados, uma conversa é iniciada.
Diferencial: Menos tempo perdido vendo imóveis irrelevantes.
5️⃣ Verificação de Imóveis e Proprietários     -- Deve ser feito

Gratuito para usuários comuns, mas com opções premium como destaque de anúncios e estatísticas avançadas.
Diferencial: Gera receita sem limitar os usuários comuns.
8️⃣ App Mobile Nativo (React Native ou Flutter) -- Legal

Criar um app rápido e otimizado com notificações push, salvamento offline e integração com WhatsApp.
Diferencial: Facilidade de acesso e engajamento diário dos usuários.
9️⃣ Parceria com Corretores e Imobiliárias      -- Legal

Criar um painel exclusivo para imobiliárias e corretores, onde possam gerenciar seus anúncios de forma prática.
Diferencial: Atrai profissionais do setor para fortalecer a plataforma.
🔟 Sistema de Recompensas para Indicações      -- Coisa de Versões futuras

Programa de indicação onde usuários ganham créditos ou descontos por indicar novos clientes.
Diferencial: Crescimento orgânico sem depender apenas de anúncios pagos.

