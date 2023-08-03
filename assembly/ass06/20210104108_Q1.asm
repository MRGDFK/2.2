.MODEL SMALL
.STACK 100H
.DATA
    MSSG DB 'ENTER 1ST DIGIT: $'
    MSSG2 DB 'ENTER 2ND DIGIT: $'
    
.CODE
MAIN PROC
    
    MOV AX,@DATA
    MOV DS,AX
    
    ;PRINT MSSG 1
    MOV AH,9
    LEA DX,MSSG
    INT 21H
    
    ;INPUT 1
    MOV AH,1
    INT 21H
    MOV BL,AL
    
    ;NEW LINE
        MOV AH,2
        MOV DL,10
        INT 21H
        MOV DL,13
        INT 21H
        
    ;PRINT MSSG 2
    MOV AH,9
    LEA DX, MSSG2
    INT 21H
    
    ;INPUT 2
    MOV AH,1
    INT 21H
    MOV BH,AL
    
    ;NEW LINE
        MOV AH,2
        MOV DL,10
        INT 21H
        MOV DL,13
        INT 21H
        
    CMP BL,BH
    JGE DISPLAY1
    JLE DISPLAY2
    
    DISPLAY1:
        MOV AH,2
        MOV DL,BL
        INT 21H
        
        ;NEW LINE
        MOV AH,2
        MOV DL,10
        INT 21H
        MOV DL,13
        INT 21H
        
        MOV AH,2
        MOV DL,BH
        INT 21H
        
        MOV AH,4CH
        INT 21H
        
     DISPLAY2:  
        MOV AH,2
        MOV DL,BH
        INT 21H
        
        ;NEW LINE
        MOV AH,2
        MOV DL,10
        INT 21H
        MOV DL,13
        INT 21H
        
        MOV AH,2
        MOV DL,BL
        INT 21H
        
        MOV AH,4CH
        INT 21H
        
     MAIN ENDP
END MAIN
        
        
        
        
    