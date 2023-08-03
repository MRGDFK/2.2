.MODEL SMALL
.STACK 100H
.DATA
    M1 DB 0AH,0DH, 'TYPE A BINARY DIGIT UP TO 16-BIT: ' , '$'
    M2 DB 0AH,0DH, 'IN HEXA IT IS: ','$'
    
    .CODE
    MAIN PROC
        MOV AX,@DATA
        MOV DS,AX
        
        MOV AH,9
        LEA DX,M1
        INT 21H
        
        MOV BX,0
        MOV AH,1
        MOV CX,16
        
        CNT: INT 21H
             CMP AL,0DH
             JE OUTPUT
             
             AND AL,01H
             SHL BX,1
             OR BL,AL
             LOOP CNT ;-- COUNTER
             
        OUTPUT: MOV AH,9
                LEA DX,M2
                INT 21H  
                
                MOV DH,4
                
                L1: MOV CL,4
                    MOV DL,BH
                    SHR DL,CL
                    MOV AH,2
                    
                    CMP DL,9
                    JG LETTER
                    ADD DL,30H
                    INT 21H
                    JMP L2 
                    
                    LETTER:
                            ADD DL,37H
                            INT 21H
                            
                            L2: ROL BX,CL
                                
                                DEC DH
                                CMP DH,0
                                JNE L1  
                    EXIT:            
                            MOV AH,4CH
                            INT 21H
                            
                            MAIN ENDP
    END MAIN