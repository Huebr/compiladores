		.text
		.globl asm_main
asm_main:
		pushq %rbp
		movq  %rsp,%rbp
		pushq %rdi
		movq $20,%rax
		pushq %rax
		call BS$BS
		movq %rax,%rdi
		movq (%rdi),%rax
		lea 8(%rax),%rax
		movq %rax,%rcx
		call *(%rax)
		popq %rdx
		popq %rdi
		pushq %rdi
		movq %rax,%rdi
		call put
		popq %rdi
		movq  %rbp,%rsp
		popq  %rbp
		ret
		.data
		BinarySearch$$: .quad 0
BS$Start:
		pushq %rbp
		movq  %rsp,%rbp
		subq $0,%rsp
		movq $1,%rax
		pushq %rax
		movq $2,%rax
		popq %rdx
		cmpq %rdx,%rax
		movq $1,%rax
		jle EndLessLabel2
		movq $0,%rax
EndLessLabel2:
		cmpq $1,%rax
		movq $1,%rax
		je EndAndLabel1
		movq $2,%rax
		pushq %rax
		movq $3,%rax
		popq %rdx
		cmpq %rdx,%rax
		movq $1,%rax
		jle EndLessLabel3
		movq $0,%rax
EndLessLabel3:
		cmpq $1,%rax
		movq $1,%rax
		je EndAndLabel1
		movq $0,%rax
EndAndLabel1:
		cmpq $1,%rax
		je ElseLabel4
IfLabel4:
		movq $1,%rax
		movq %rax,16(%rbp)
		jmp DoneLabel4
ElseLabel4:
		movq $2,%rax
		movq %rax,16(%rbp)
DoneLabel4:
		movq 16(%rbp),%rax
		movq  %rbp,%rsp
		popq  %rbp
		ret
BS$BS:
		pushq %rbp
		movq  %rsp,%rbp
		pushq %rdi
		movq $8,%rdi
		call mjcalloc
		popq %rdi
		leaq  BS$$,%rdx
		movq %rdx,(%rax)
		movq  %rbp,%rsp
		popq  %rbp
		ret
		.data
		BS$$: .quad 0
		.quad BS$Start
		.quad BS$BS
