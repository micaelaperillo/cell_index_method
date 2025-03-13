import random 

static_path = 'files/static/'
dynamic_path = 'files/dynamic/'

def generate_files(n):
    generate_static_file(n)
    generate_dynamic_file(n)

def generate_static_file(n):
    with open(f'{static_path}file.txt', 'w') as file:
        L = random.randint(50, 150)
        file.write(f'{n}\n{L}\n')
        
        for _ in range(n):
            file.write(f'{random.randint(1, 10)} 1.000\n')

def generate_dynamic_file(n):
    with open(f'{dynamic_path}file.txt', 'w') as file:
        file.write(f'0\n')
        for i in range(n): 
            x, y = random.randint(0, 100), random.randint(0, 100)
            file.write(f'{x} {y}\n')

def main():
    n = 100
    generate_files(n)

if __name__ == '__main__':
    main()