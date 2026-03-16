# Sistema de Cadastro de Eventos

Este projeto é um protótipo em Java, desenvolvido no paradigma de **orientação a objetos**, com execução em **console**.  
O objetivo é simular um sistema de cadastro e notificação de eventos que ocorrem na cidade.

## Funcionalidades
- Cadastro de usuários (mínimo 3 atributos: nome, email, telefone).
- Cadastro de eventos com atributos obrigatórios:
  - nome, endereço, categoria, horário, descrição.
- Categorias pré-definidas (ex.: festas, esportivos, shows).
- Consulta de eventos cadastrados.
- Confirmação e cancelamento de participação em eventos.
- Listagem de eventos ativos, futuros e já ocorridos.
- Ordenação por horário usando `LocalDateTime`.

## Persistência
- As informações dos eventos são salvas em `events.data`.
- Ao iniciar, o sistema carrega os eventos a partir desse arquivo.

## Como executar
1. Clone este repositório:
   ```bash
   git clone https://github.com/dayanasimoes2011-svg/sistema-eventos.git
   ```
