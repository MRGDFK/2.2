.MODEL SMALL
.STACK 100H
.DATA
   FIRST      DB  'ENTER HEX DIGIT : $'
   DECIMAL    DB  'DECIMAL FORM : $'
   CONTINUE   DB  'TRY AGAIN ? PRESS 'Y' FOR CONTINUE 'N' FOR EXIT : $'
   ILLEGAL    DB  ' OPPS!!! Illegal Character -Please  Enter 0..9 or A..F : $'
  

.CODE
MAIN PROC
    ;DATA SEGMENT INITIALIZE
    
    MOV AX,@DATA
    MOV DS,AX        ;CONVERTING DATA SEGMENT INTO CODE SEGMENT

    
      INPUT: 
       ;PRINTING THE STRING                   
       MOV AH, 9                  
       LEA DX, FIRST             
       INT 21H

       ;TAKING USER INPUT                  
       MOV AH, 1                  
       INT 21H
       MOV BL,AL
       
         
        
       
                                    
        CMP BL,48             ; COMPARING WITH LESS THAN 0
        JL  ILLEGAL_INPUT 
        
        
     
        CMP BL,70              ;COMPARING WITH BIGGER THAN F        
        JG  ILLEGAL_INPUT  
        
       
     
     
        CMP BL,57                ; IF IT IS BETWEEN 0-9
        JLE CHANGE_DIGIT  
        
       
                                  
                               
        CMP BL,64
        JLE ILLEGAL_INPUT             ; IF IT IS BETWEEN 58-64 
        
        
        ;NEW LINE
        MOV AH,2
        MOV DL,10
        INT 21H
        MOV DL,13
        INT 21H
       
      
        MOV AH,9
        LEA DX,DECIMAL
        INT 21H
     
        SUB BL,17
     
        MOV AH,2
        MOV DL,49             ;PRINTING 1 IN FIRST PLACE 
        INT 21H
        MOV DL,BL             ;PRINTING EQUIVALENT VALUE
        INT 21H      
        JMP YES_NO
       
       YES_NO:
      
       ;NEW LINE
       MOV AH,2
       MOV DL,10
       INT 21H
       MOV DL,13
       INT 21H
      
       MOV AH,9
       LEA DX,CONTINUE
       INT 21H 
       
       MOV AH,1
       INT 21H
       MOV CL,AL
       
       CMP CL,'Y'
       
       ;NEW LINE
       MOV AH,2
       MOV DL,10
       INT 21H
       MOV DL,13
       INT 21H
       
       JE INPUT           ;IF Y THEN REPEAT
       
       CMP CL,'N'
       JE EXIT            ;IF N THEN STOP
       
       JMP ILLEGAL_INPUT   ;OTHERWISE WRONG
       
       
       CHANGE_DIGIT: 
       
       ;NEW LINE
       MOV AH,2
       MOV DL,10
       INT 21H
       MOV DL,13
       INT 21H
       
       MOV AH,9
       LEA DX,DECIMAL
       INT 21H
     
       MOV AH,2
       MOV DL,BL           ;IF 0-9 THEN AS IT IS
       INT 21H
       JMP YES_NO


      ILLEGAL_INPUT: 
      
      ;NEW LINE
       MOV AH,2
       MOV DL,10
       INT 21H
       MOV DL,13
       INT 21H
      
      
       MOV AH, 9                  
       LEA DX, ILLEGAL           
       INT 21H
       
       ;NEW LINE
       MOV AH,2
       MOV DL,10
       INT 21H
       MOV DL,13
       INT 21H
       JMP INPUT




        EXIT:
     
    MOV AH,4CH
    INT 21H
    MAIN ENDP
END MAIN 