.CODE
MAIN PROC
    MOV AX,@DATA
    MOV DS,AX
    
    LEA DX,INPUT            
    MOV AH,9
    INT 21H 
    
    
    LEA SI,CLASS
    MOV BH,4
    MOV BL,16 
    
    CALL DISPLAY_ARRAY
    LEA DI, AVERAGE
    LEA SI, CLASS
    ADD S1,12
    MOV CX,4 
    
    COMPUTE_AVERAGE:
    XOR AX,AX
    MOV DX,4   
    
    SUM:
    XOR BH,BH
    MOV BL,[S1]
    ADD AX,AX
    INC S1
    DEC DX
    JNZ SUM
    MOV BX,4
    DIV BX
    
    MOV [D1],AX
    ADD D1,2
    ADD S1,12
    
    LOOP COMPUTE_AVERAGE
    
    LEA DX,RESULT
    MOV AH,9
    INT 21H
    
    LEA S1, AVERAGE
    LEA D1,CLASS
    MOV CX,4
    
    PRINT_RESULT:
    MOV BX,12
    MOV AH,2
    
    S_NAME:
    MOV DL,[D1]
    INT 21H
    INC D1
    DEC BX
    
    JNZ S_NAME
    
    MOV DL.,"="
    INT 21H
    MOV DL,20H
    INT 21H
    
    MOV DL,20H
    INT 21H
    
    XOR AH,AH
    MOV AL,[S1]
    
    CALL DECIMAL_PRINT
    MOV AH,2
    MOV DL,0DH
    INT 21H
    
    ADD S1,2
    ADD D1,4
    LOOP PRINT_RESULT
    
    MOV AH,4CH
    INT 21H
    MAIN ENDP
DISPLAY_ARRAY PROC
    PUSH AX
    PUSH CX
    PUSH DX
    PUSH S1
    
    MOV CX,BX
    
    OUTER_LOOP:
        MOV CL,BL
        MOV AH,2
        
     PRINT_NAME:
        MOV DL,[S1]
        INT 21H
        INC S1
        DEC CL
        
        CMP CL,4
        JG PRINT_NAME
        
        MOV DL,20H
        INT 21H
        
        INNER_LOOP:
            MOV AH,2
            MOV DL,20H
            INT 21H
            XOR AH,AH
            MOV AL,[S1]
            
            CALL DECIMAL_PRINT
            
            INC S1
            DEC CL
            
            JNZ INNER_LOOP
            
            MOV AH,2
            MOV DL,0DH
            INT 21H
            MOV DL,OAH
            INT 21H
            
            DEC CH
            JNZ OUTER_LOOP
            
            POP S1
            POP DX
            POP CX
            POP AX
            
            RET
            
            DISPLAY_ARRAY ENDP
DECIMAL_PRINT PROC
    PUSH BX
    PUSH CX
    PUSH DX
    
    XOR CX,CX
    MOV DX,10
    
    OUTPUT:
    XOR DX,DX
    DIV BX
    PUSH DX
    INC CX
    OR AX,AX
    
    JNE OUTPUT
    MOV AH,2
    PRINT:
    POP DX
    OR DL,30H
    INT 21H
    LOOP PRINT
    POP DX
    POP CX
    POP BX
    
    RET DECIMAL_PRINT ENDP
END MAIN
