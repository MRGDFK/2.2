 .MODEL SMALL
 .STACK 100H

 .DATA
   FIRST  DB  'ENTER M= $'
   SECOND  DB  0DH,0AH,'ENTER  N = $'
   RESULT  DB  0DH,0AH,'GCD is = $'
 
 .CODE
   MAIN PROC
     MOV AX, @DATA                ; initialize DS
     MOV DS, AX

     LEA DX, FIRST            ; load and display the string PROMPT_1
     MOV AH, 9
     INT 21H
     ; FOR M
     CALL READ_NUMBER                  

     PUSH AX                     

     LEA DX, SECOND           
     MOV AH, 9
     INT 21H
     ;FOR N
     CALL READ_NUMBER                 

     MOV BX, AX                   ; BX=AX

     POP AX                       
     
     REPEAT:                     
       XOR DX, DX                 ; CLEARING DX
       DIV BX                     ; set AX=DX:AX\BX ,  AX=DX:AX%BX

       CMP DX, 0                  
       JE END_LOOP                ; IF CX=0

       MOV AX, BX                 ;  AX=BX
       MOV BX, DX                 ;  BX=DX
     JMP REPEAT                    

     END_LOOP:                   

     LEA DX, RESULT         
     MOV AH, 9
     INT 21H

     MOV AX, BX                   ; set AX=BX

     CALL DISPLAY_RESULT           

     MOV AH, 4CH                  ; return control to DOS
     INT 21H
   MAIN ENDP



 ;**************************************************************************;
 
 ;**************************************************************************;

 READ_NUMBER PROC
   

   PUSH BX                       
   PUSH CX                        
   PUSH DX                        

   JMP READ                     

   SKIP_BACKSPACE:               
   MOV AH, 2                     
   MOV DL, 20H                    ; DL=' '
   INT 21H                        

   READ:                         
   XOR BX, BX                     ; CLEARING BX
   XOR CX, CX                     ; CLEARING CX
   XOR DX, DX                     ; CLEARING DX

   MOV AH, 1                    
   INT 21H                       

   CMP AL, "-"                   
   JE @MINUS                      ; IF AL="-"

   CMP AL, "+"                   
   JE @PLUS                       ; IF AL="+"

   JMP SKIP_INPUT               

   @MINUS:                      
   MOV CH, 1                      ; CH=1
   INC CL                         
   JMP @INPUT                   
   
   @PLUS:                        
   MOV CH, 2                      ; CH=2
   INC CL                        

   @INPUT:                        
     MOV AH, 1                   
     INT 21H                     

     SKIP_INPUT:               

     CMP AL, 0DH                  ; COMAPARE  AL with CARRIAGE RETURN
     JE @END_INPUT                

     CMP AL, 8H                   ; COMPARE AL with 8H
     JNE @NOT_BACKSPACE           ; IF AL!=8

     CMP CH, 0                    ; COMPARE CH with 0
     JNE @CHECK_REMOVE_MINUS      ; IF CH!=0

     CMP CL, 0                    ; compare CL with 0
     JE SKIP_BACKSPACE            ; IF CL=0
     JMP @MOVE_BACK               

     @CHECK_REMOVE_MINUS:       

     CMP CH, 1                    ; compare CH with 1
     JNE @CHECK_REMOVE_PLUS       ; IF CH!=1

     CMP CL, 1                    ; compare CL with 1
     JE @REMOVE_PLUS_MINUS        ; IF CL=1

     @CHECK_REMOVE_PLUS:          

     CMP CL, 1                    ; compare CL with 1
     JE @REMOVE_PLUS_MINUS        ; IF CL=1
     JMP @MOVE_BACK               

     @REMOVE_PLUS_MINUS:        
       MOV AH, 2                  
       MOV DL, 20H                ; DL=' '
       INT 21H                    

       MOV DL, 8H                 ; DL=8H
       INT 21H                   

       JMP READ                
                                  
     @MOVE_BACK:                 

     MOV AX, BX                   ; AX=BX
     MOV BX, 10                   ; BX=10
     DIV BX                       ; AX=AX/BX

     MOV BX, AX                   ; BX=AX

     MOV AH, 2                    
     MOV DL, 20H                  ;  DL=' '
     INT 21H                      

     MOV DL, 8H                   ; DL=8H
     INT 21H                      

     XOR DX, DX                   ; CLEARING DX
     DEC CL                       ; CL=CL-1

     JMP @INPUT                 

     @NOT_BACKSPACE:              

     INC CL                       ; set CL=CL+1

     CMP AL, 30H                  ; compare AL with 0
     JL @ERROR                    ; IF AL<0

     CMP AL, 39H                  ; compare AL with 9
     JG @ERROR                    ; IF AL>9

     AND AX, 000FH                ; ASCII TO DECIMAL

     PUSH AX                   

     MOV AX, 10                   ;  AX=10
     MUL BX                       ;  AX=AX*BX
     MOV BX, AX                   ;  BX=AX

     POP AX                      

     ADD BX, AX                   ;BX=AX+BX
     JS @ERROR                    ; SF=1
   JMP @INPUT                    

   @ERROR:                        

   MOV AH, 2                      
   MOV DL, 7H                     ; DL=7H
   INT 21H                        

   XOR CH, CH                     ; CLEARING CH

   @CLEAR:                        
     MOV DL, 8H                   ; DL=8H
     INT 21H                      ;

     MOV DL, 20H                  ; DL=' '
     INT 21H                      

     MOV DL, 8H                   ; DL=8H
     INT 21H                      
   LOOP @CLEAR                    ; IF CX!=0

   JMP READ                      

   @END_INPUT:                  

   CMP CH, 1                      
   JNE @EXIT                      ; IF CH!=1
   NEG BX                         ; negate BX

   @EXIT:                       

   MOV AX, BX                     ; AX=BX

   POP DX                        
   POP CX                         
   POP BX                         

   RET                            ; RETURN
 READ_NUMBER ENDP
                                  
 
 ;**************************************************************************;

 ;**************************************************************************;

 DISPLAY_RESULT PROC
  
  
 
  

   PUSH BX                        
   PUSH CX                       
   PUSH DX                       

   CMP AX, 0                      ; compare AX with 0
   JGE @START                     ; IF AX>=0

   PUSH AX                       

   MOV AH, 2                      
   MOV DL, "-"                    ; DL='-'
   INT 21H                        

   POP AX                        

   NEG AX                         ;  2's complement of AX

   @START:                        

   XOR CX, CX                     ; CLEARING CX
   MOV BX, 10                     ; BX=10

   @OUTPUT:                       
     XOR DX, DX                   ; CLEARING DX
     DIV BX                       ;  AX / BX
     PUSH DX                     
     INC CX                       
     OR AX, AX                   
   JNE @OUTPUT                    ; IF ZF=0

   MOV AH, 2                      

   @DISPLAY:                      
     POP DX                       
     OR DL, 30H                   ; DECIMAL TO ASCII
     INT 21H                      
   LOOP @DISPLAY                  ; IF CX!=0

   POP DX                         
   POP CX                         
   POP BX                         

   RET                            ; RETURN
 DISPLAY_RESULT ENDP

 ;**************************************************************************;
 
 ;**************************************************************************;
