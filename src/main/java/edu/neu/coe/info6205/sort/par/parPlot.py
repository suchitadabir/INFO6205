#!/usr/bin/env python3

#python3 -m pip install -U pandas seaborn matplotlib
import os
import pandas as pd
import seaborn as sns
from pathlib import Path
import matplotlib.pyplot as plt

# Benchmark CSV File
#file_path = '/Users/suchitadabir/NEU_MS/github_repos/INFO6205/ThreeSumBenchmark.csv'
file_path = '/Users/suchitadabir/NEU_MS/github_repos/INFO6205/src/ParSort.csv'
file_dir = os.path.join(os.path.dirname(file_path), "assignment5")
Path(file_dir).mkdir(parents=True, exist_ok=True)

# Read the CSV file into a DataFrame
col_names = ["arraysize", "threadcount", "cobyas", "time"]
df = pd.read_csv(file_path, sep=",")
df.columns = col_names
df['arraysize'] = df['arraysize'] / 1000000

araysizes = [1.0, 2.0, 4.0, 8.0, 16.0]
numthreads = [2, 4, 8, 16]
cobyas = [0.4, 0.6]

for nt in numthreads:
    print("threadcount = {}".format(nt))
    df_const_thrd = df.loc[df['threadcount'] == nt]
    # Create a seaborn plot
    plt.figure()
    sns.lineplot(data=df_const_thrd,
                 x='cobyas',
                 y='time',
                 hue='arraysize',
                 marker='o',
                 palette="tab10")

    # Adding titles and labels
    plt.title(
        'Cutoff / Array Size vs. Avg. Time for different Array sizes ; # Threads = {}'
        .format(nt))
    plt.xlabel('Cutoff / Array Size')
    plt.ylabel('Avg Time (ms)')
    plt.legend(title='Array sizes (Million)', ncol=2)
    plt.grid()

    # Display the plot
    plt_filename = os.path.join(file_dir, "cta_{}threads.pdf".format(nt))
    plt.savefig(plt_filename, bbox_inches='tight')
    #plt.show()
    plt.close()

for ars in araysizes:
    print("arraysize = {}".format(ars))
    df_const_as = df.loc[df['arraysize'] == ars]
    # Create a seaborn plot
    plt.figure()
    sns.lineplot(data=df_const_as,
                 x='cobyas',
                 y='time',
                 hue='threadcount',
                 marker='o',
                 palette="tab10")

    # Adding titles and labels
    plt.title(
        'Cutoff / Array Size vs. Avg. Time for different Parallelism levels ; Array Size = {}M'
        .format(int(ars)))
    plt.xlabel('Cutoff / Array Size')
    plt.ylabel('Avg Time (ms)')
    plt.legend(title='Parallelism levels (Threads)', ncol=2)
    plt.grid()

    # Display the plot
    plt_filename = os.path.join(file_dir, "ctp_{}M.pdf".format(int(ars)))
    plt.savefig(plt_filename, bbox_inches='tight')
    #plt.show()
    plt.close()

if 0:
    for co in cobyas:
        print("cobyas = {}".format(co))
        df_const_co = df.loc[df['cobyas'] > co - 0.01]
        df_const_co = df_const_co.loc[df_const_co['cobyas'] < co + 0.01]
        # Create a seaborn plot
        plt.figure()
        sns.lineplot(data=df_const_co,
                     x='threadcount',
                     y='time',
                     hue='arraysize',
                     marker='o',
                     palette="tab10")

        # Adding titles and labels
        plt.title(
            'Parallelism levels vs. Avg. Time for different Array sizes ; Cutoff / Array Size = {}'
            .format(co))
        plt.xlabel('Parallelism level')
        plt.ylabel('Avg Time (ms)')
        plt.legend(title='Cutoff / Array Size', ncol=1)
        plt.grid()

        # Display the plot
        plt_filename = os.path.join(file_dir, "pta_{}co.pdf".format(co))
        plt.savefig(plt_filename, bbox_inches='tight')
        #plt.show()
        plt.close()
