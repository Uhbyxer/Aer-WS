       01  PURCHASES-DISC.
           03  REQUEST-TYPE                 PIC X.
           03  RET-CODE                     PIC XX.
           03  CustomerId                   PIC X(8).
           03  CustomerLastName             PIC X(20).
           03  CustomerFirstName            PIC X(20).
           03  CustomerCompany              PIC X(30).
           03  CustomerAddr1                PIC X(30).
           03  CustomerAddr2                PIC X(30).
           03  CustomerCity                 PIC X(20).
           03  CustomerCountry              PIC X(30).
           03  CustomerArea                 PIC X(20).
            03  CustomerProvince REDEFINES CustomerArea PIC X(20).
            03  CustomerCounty   REDEFINES CustomerArea PIC X(20).
            03  CustomerRegion   REDEFINES CustomerArea PIC 9(20).
            03  CustomerState    REDEFINES CustomerArea PIC X(20).
           03  CustomerMailCode             PIC X(20).
           03  CustomerPhone                PIC X(20).
           03  CustomerLastUpdateDate       PIC X(8).
           03  PurchaseCount                PIC 9(3) USAGE COMP.
           03  Purchase OCCURS 0 TO 99 TIMES
               DEPENDING ON PurchaseCount.
             04  PurchaseId                 PIC 9(5).
             04  ProductName                PIC X(30).
             04  Amount                     PIC 9(2).
             04  Price                      PIC 9(8)V99.
           03  RETURN-COMMENT               PIC X(50).
