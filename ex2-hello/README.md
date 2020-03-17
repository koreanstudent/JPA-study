# 정리
	- @GeneratedValue : 자동 생성 
		- IDENTITY : 기본 키 생성을 데이터베이스에 위임,MYSQL(AUTO_INCREMENT) persist() 시점에 즉시 INSERT SQL 실행 하고 DB에서 식별자를 조회
		- SEQUENCE : 데이터베이스 시퀀스 오브젝트 사용, ORACLE
			- @SequenceGenerator 필요
		- TABLE: 키 생성용 테이블 사용, 모든 DB에서 사용
			- @TableGenerator 필여
		- AUTO : 방언에 따라 자동 지정, 기본값