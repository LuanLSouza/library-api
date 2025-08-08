# API Livraria

API REST desenvolvida em Java com Spring Boot para gerenciamento de uma livraria.

## 🛠 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.x
- Spring Security
- Maven
- BCrypt para criptografia de senhas
- Autenticação Basic Auth

## 📋 Funcionalidades

- Sistema de autenticação e autorização com Spring Security
- Cadastro de usuários com senhas criptografadas
- Controle de acesso baseado em roles (ADMIN, USER)
- Login personalizado com página específica
- Endpoints protegidos com autenticação
- MapStruct para criação de mappers

## 🔒 Segurança

O projeto utiliza as seguintes configurações de segurança:

- Basic Auth para autenticação
- Proteção CSRF desabilitada para API REST
- Endpoints públicos:
  - `/login`
  - POST `/usuarios/**`
- Demais endpoints requerem autenticação
- Senhas criptografadas com BCrypt

## 📝 Observações

Projeto desenvolvido como parte do curso "[ATUALIZADO 2025] Profissionalize-se em Java com Spring Boot em um Guia Completo e atualizado do Zero ao Deploy na AWS" do professor Dougllas Sousa.
