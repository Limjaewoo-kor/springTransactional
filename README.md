# springTransactional
spring @Transactional Required

물리 트랜잭션과 논리 트랜잭션의 개념 정리


원칙
모든 논리 트랜잭션이 커밋되어야 물리 트랜잭션이 커밋된다. 
하나의 논리 트랜잭션이라도 롤백되면 물리 트랜잭션은 롤백된다. 
