import matplotlib.pyplot as plt
import matplotlib.image as mpimg
import numpy as np

# Carregar a imagem
org_img = mpimg.imread("img-broadway_tower.jpg")

# Exibir a imagem
plt.imshow(org_img)
plt.title('Imagem Original')
plt.xlabel('x')
plt.ylabel('y')
plt.show()

print(f'Tipo da imagem: {type(org_img)}')
print(f'Dimensão da imagem: {org_img.shape}')

pixel_value = org_img[50, 100] # Posição (x,y) na imagem corresponde a posição [y,x] no array
print(f'Valor RGB do pixel (50, 100): {pixel_value}')

red_channel = org_img[:, :, 0]
plt.imshow(red_channel, cmap='gray')
plt.title('Canal Vermelho')
plt.show()

img_redbox = np.array(org_img, dtype=org_img.dtype)
img_redbox[10:60, 10:60] = [255, 0, 0]
plt.imshow(img_redbox)
plt.title('Imagem com Região Vermelha')
plt.show()

# Cortar a imagem
cropped_img = org_img[50:150, 650:850]

# Exibir a imagem cortada
plt.imshow(cropped_img)
plt.title('Imagem Cortada')
plt.show()

# Rotacionar o array 90 graus para a direita
rotated_img = np.rot90(org_img, k=-1)

# Exibir a imagem rotacionada
plt.imshow(rotated_img)
plt.title('Imagem Rotacionada')
plt.show()

# Salvar as imagens manipuladas
plt.imsave("cropped_image.jpg", cropped_img)