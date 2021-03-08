create table customer(
    CU_ID NUMBER(10,0) primary key not null,
    CU_OLD_ID NUMBER(10,0) not null,
    CU_TYPE CHAR(1),
    CU_ORIGIN_DATE DATE not null,
    CU_REG_DATE DATE,
    CU_PHONE_NUMBER NUMBER(9,0),
    CU_NAME VARCHAR2(64)
);

create table vehicle(
    VEH_ID NUMBER(10,0) primary key,
    VEH_OLD_ID NUMBER(10,0) not null,
    VEH_CU_ID_NEW int not null references customer(CU_ID),
    VEH_CU_ID NUMBER(10,0) not null,
    VEH_VIN VARCHAR2(17) unique,
    VEH_REG_DATE DATE not null,
    VEH_REG_COUNTRY VARCHAR2(100),
    VEH_MANUFACTURER VARCHAR2(100),
    VEH_TYPE VARCHAR2(100),
    VEH_BODY VARCHAR2(100),
    VEH_ENGINE VARCHAR2(100),
    VEH_RESTRAINT VARCHAR2(100),
    VEH_MODEL VARCHAR2(100),
    VEH_YEAR NUMBER(4,0),
    VEH_PLANT VARCHAR2(100),
    VEH_SERIAL_NUMBER VARCHAR2(100),
    
    constraint VEH_OLD_ID_cq check (VEH_OLD_ID > 0)
);

create table REPAIR(
    RE_ID NUMBER(10,0) primary key,
    RE_OLD_ID NUMBER(10,0) not null,
    RE_VEH_ID NUMBER(10,0) not null references vehicle(VEH_ID),
    RE_VEH_OLD_ID NUMBER(10,0) not null,
    RE_DET_ID NUMBER(10,0) not null,
    RE_SHOP_ID NUMBER(10,0) not null,
    RE_BILL_DATE DATE not null,
    constraint re_old_id_cq check(RE_OLD_ID>0),
    
    CONSTRAINT constraint_name UNIQUE (re_shop_id, re_old_id)
);

create table REPAIR_DETAIL(
    RD_ID NUMBER(10,0) primary key not null CHECK (RD_ID>=0),
    RD_TYPE VARCHAR2(100),
    RD_PRICE NUMBER(9,2)
);

create table REPAIR_SHOP(
    RS_ID NUMBER(10,0) primary key not null,
    RS_NAME VARCHAR2(100) not null,
    RS_MANAGER VARCHAR2(100) not null,
     constraint RS_ID_cq CHECK (RS_ID>=0)
);

create sequence customer_seq;
create sequence vehicle_seq;
create sequence repair_seq;

select * from customer;
select * from repair;


select count(*) from repair;
select count(*) from vehicle;
select count(*) from customer;

delete from customer;
delete from vehicle;
delete from repair;

commit;