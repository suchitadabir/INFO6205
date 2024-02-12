#!/usr/bin/env python3

import os
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt

file_path = '/Users/suchitadabir/NEU_MS/github_repos/INFO6205/UFClientData.csv'
file_dir = os.path.dirname(file_path)

df = pd.read_csv(file_path)

# Create a seaborn plot
plt.figure()
#sns.lineplot(data=df, x='N', y='Time', hue='Function', marker='o')
sns.lineplot(data=df, x='n', y='m')

# Adding titles and labels
plt.title('Relationship between number of objects (n) and number of pairs (m) ')
plt.xlabel('n')
plt.ylabel('m')
#plt.legend(title='Relationship')
plt.grid()

# Display the plot
plt_filename = os.path.join(file_dir, "UfClient.pdf")
plt.savefig(plt_filename, bbox_inches='tight')
plt.show()
plt.close()
