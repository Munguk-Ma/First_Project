-- 테이블 삭제
drop table useraccount;
drop table usermemo;
drop table userinfo;
drop table friend;
drop table messagebox;
drop sequence memoseq;

--메모일련번호
create sequence memoseq;
create sequence meseq;

--유저정보
create table useraccount (
	name varchar2(20) not null,			-- 회원이름 문자열 null존재불가
	id varchar2(20) primary key,		-- 아이디 null존재불가 
	password varchar2(20) not null		-- 비밀번호  null존재불가 
);

--메모정보
create table usermemo (
	memoseq varchar2(60) primary key,
    title varchar2(60) not null, 	--메모 제목
    content varchar2(2000) not null,--메모내용
    indate date default sysdate,    --메모작성일
    id varchar2(20),				--작성자
	constraint id_fk 
	foreign key (id) 
	references useraccount(id)	
);


create table userinfo 
(   
    id varchar2(20) primary key,             --회원 ID
    name varchar2(20) not null,              --회원 이름
    hint varchar2(100) not null              --회원 비밀번호찾기 힌트
)


create table friend 
(
	myid varchar2(20) not null,              --회원 ID
	myname varchar2(20)  not null,           --회원 이름
	friendid varchar2(20) not null,          --친구추가할 ID
	friendname varchar2(20) not null,        --친구추가할 dlfma
	status varchar2(20) not null             --친구 현황
)

create table messagebox
(
	messageseq varchar(2000) primary key,    --메세지 번호
	sendid varchar(20) not null,             --보내는사람 ID
	content varchar(2000) not null,          --메세지 내용
	reid varchar(20) not null,               --받는사람 ID
    status varchar(20) not null,             --메세지를 읽었는지 여부
	senddate date default sysdate,           --보낸 날짜
	constraint reid_fk                       --받는사람 ID는 FK키로 설정하여 없는아이디로는 메시지 보내기불가능
	foreign key (reid) 
	references useraccount(id)	
)


-- 커밋
commit;


delete useraccount;
delete usermemo;

