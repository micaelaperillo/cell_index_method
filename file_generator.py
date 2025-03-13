import random 

static_path = 'files/static/'
dynamic_path = 'files/dynamic/'

def generate_static_file(n):
    for i in range(n):
        with open(f'{static_path}file_{i}.txt', 'w') as file:
            N = random.randint(100, 300) # Total number of particles
            L = random.randint(50, 150)
            file.write(f'{N}\n{L}\n')

            for _ in range(N):
                file.write(f'{random.randint(1, 10)}\n')

def generate_dynamic_file(n):
    for i in range(n):
        with open(f'{dynamic_path}file_{i}.txt', 'w') as file:
            file.write(f'0\n')
            for i in range(100): # N = 100 for dynamic files
                x, y = random.randint(0, 100), random.randint(0, 100)
                file.write(f'{x} {y}\n')

def main():
    n = 10
    generate_static_file(n)
    generate_dynamic_file(n)

if __name__ == '__main__':
    main()