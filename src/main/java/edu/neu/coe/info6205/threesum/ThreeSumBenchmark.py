#!/usr/bin/env python3

#python3 -m pip install -U pandas seaborn matplotlib
import os
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt

# Benchmark CSV File
file_path = '/Users/suchitadabir/NEU_MS/github_repos/INFO6205/ThreeSumBenchmark.csv'
file_dir = os.path.dirname(file_path)

# Read the CSV file into a DataFrame
df = pd.read_csv(file_path)

# Create a seaborn plot
plt.figure()
sns.lineplot(data=df, x='N', y='Time', hue='Function', marker='o')

# Adding titles and labels
plt.title('Time Complexity of Different Methods')
plt.xlabel('N')
plt.ylabel('Time')
plt.legend(title='Methods')
plt.grid()

# Display the plot
plt_filename = os.path.join(file_dir, "tc.pdf")
plt.savefig(plt_filename, bbox_inches='tight')
plt.show()
plt.close()
