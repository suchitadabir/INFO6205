#!/usr/bin/env python3

#python3 -m pip install -U pandas seaborn matplotlib
import os
import pandas as pd
import numpy as np
import seaborn as sns
from pathlib import Path
import matplotlib.pyplot as plt

# Benchmark CSV File
file_path = '/Users/suchitadabir/NEU_MS/github_repos/INFO6205/src/Assignment6.csv'
file_dir = os.path.join(os.path.dirname(file_path), "assignment6")
Path(file_dir).mkdir(parents=True, exist_ok=True)

# Read the CSV file into a DataFrame
col_names = [
    "sorter", "instrumented", "insurance", "nocopy", "nWords", "wordLength",
    "nRuns", "rawTime", "normTime", "hitsMean", "hitsStdev", "hitsNorm",
    "copies", "copiesNorm", "swapsMean", "swapsStdev", "swapsNorm",
    "comparesMean", "comparesStdev", "comparesNorm"
]
df = pd.read_csv(file_path,names=range(len(col_names)))
df.columns = col_names

predictors = ["hitsMean", "comparesMean", "swapsMean"]
sorting_alogs = ["MergeSort", "QuickSort", "HeapSort"]
df["sortAlgo"] = "algo"
df.loc[df['sorter'].str.startswith('MergeSort'), 'sortAlgo'] = "MergeSort"
df.loc[df['sorter'].str.startswith('QuickSort'), 'sortAlgo'] = "QuickSort"
df.loc[df['sorter'].str.startswith('Heapsort'), 'sortAlgo'] = "HeapSort"
print(df)
formatted_csv = os.path.join(file_dir, os.path.basename(file_path))
df.to_csv(formatted_csv.replace(".csv", "Formatted.csv"), index=False)

df_time = df.loc[df['instrumented'] == False]
df_time = df_time[(df_time['sortAlgo'] != 'MergeSort') | ((df_time['sortAlgo'] == 'MergeSort') & (df_time['insurance'] == True) & (df_time['nocopy'] == True))]
print(df_time[["sorter", "nWords", "nRuns", "rawTime"]])
# Create a seaborn plot
plt.figure()
sb = sns.lineplot(data=df_time,
                  x='nWords',
                  y='rawTime',
                  hue='sortAlgo',
                  marker='o',
                  palette="tab10")
#sb.set(xscale="log", yscale="log")
plt.xscale('log', base=2)
plt.yscale('log', base=2)
# Adding titles and labels
plt.title('Number of Elements vs. Raw Time for different Sorting Algorithms')
plt.xlabel('Number of Elements')
plt.ylabel('Raw Time (ms)')
plt.legend(title='Sorting Algorithms')
plt.grid()
# Display the plot
plt_filename = os.path.join(file_dir, "timeVsalgo.pdf")
plt.savefig(plt_filename, bbox_inches='tight')
#plt.show()
plt.close()

if 1:

    df_hits = df.loc[df['instrumented'] == True]
    df_hits = df_hits[(df_hits['sortAlgo'] != 'MergeSort') | ((df_hits['sortAlgo'] == 'MergeSort') & (df_hits['insurance'] == True) & (df_hits['nocopy'] == True))]
    #print(df_hits)
    # Create a seaborn plot
    plt.figure()
    sb = sns.lineplot(data=df_hits,
                      x='nWords',
                      y='hitsMean',
                      hue='sortAlgo',
                      marker='o',
                      palette="tab10")
    #sb.set(xscale="log", yscale="log")
    plt.xscale('log', base=2)
    plt.yscale('log', base=2)
    # Adding titles and labels
    plt.title('Number of Elements vs. Avg. Hits for different Sorting Algorithms')
    plt.xlabel('Number of Elements')
    plt.ylabel('Avg. Hits')
    plt.legend(title='Sorting Algorithms')
    plt.grid()
    # Display the plot
    plt_filename = os.path.join(file_dir, "hitsVsalgo.pdf")
    plt.savefig(plt_filename, bbox_inches='tight')
    #plt.show()
    plt.close()

    df_comparisons = df.loc[df['instrumented'] == True]
    df_comparisons = df_comparisons[(df_comparisons['sortAlgo'] != 'MergeSort') | ((df_comparisons['sortAlgo'] == 'MergeSort') & (df_comparisons['insurance'] == True) & (df_comparisons['nocopy'] == True))]
    #print(df_comparisons)
    # Create a seaborn plot
    plt.figure()
    sb = sns.lineplot(data=df_comparisons,
                      x='nWords',
                      y='comparesMean',
                      hue='sortAlgo',
                      marker='o',
                      palette="tab10")
    #sb.set(xscale="log", yscale="log")
    plt.xscale('log', base=2)
    plt.yscale('log', base=2)
    # Adding titles and labels
    plt.title('Number of Elements vs. Avg. Comparisons for different Sorting Algorithms')
    plt.xlabel('Number of Elements')
    plt.ylabel('Avg. Comparisons')
    plt.legend(title='Sorting Algorithms')
    plt.grid()
    # Display the plot
    plt_filename = os.path.join(file_dir, "comparisonsVsalgo.pdf")
    plt.savefig(plt_filename, bbox_inches='tight')
    #plt.show()
    plt.close()


    df_swaps = df.loc[df['instrumented'] == True]
    df_swaps = df_swaps[(df_swaps['sortAlgo'] != 'MergeSort') | ((df_swaps['sortAlgo'] == 'MergeSort') & (df_swaps['insurance'] == True) & (df_swaps['nocopy'] == True))]
    #print(df_swaps)
    # Create a seaborn plot
    plt.figure()
    sb = sns.lineplot(data=df_swaps,
                      x='nWords',
                      y='swapsMean',
                      hue='sortAlgo',
                      marker='o',
                      palette="tab10")
    #sb.set(xscale="log", yscale="log")
    plt.xscale('log', base=2)
    plt.yscale('log', base=2)
    # Adding titles and labels
    plt.title('Number of Elements vs. Avg. Swaps for different Sorting Algorithms')
    plt.xlabel('Number of Elements')
    plt.ylabel('Avg. Swaps')
    plt.legend(title='Sorting Algorithms')
    plt.grid()
    # Display the plot
    plt_filename = os.path.join(file_dir, "swapsVsalgo.pdf")
    plt.savefig(plt_filename, bbox_inches='tight')
    #plt.show()
    plt.close()


for sa in sorting_alogs:
    print("     Algorithm = ",sa)
    df_algo = df.loc[df['instrumented'] == True]
    df_algo = df_algo.loc[df_algo['sortAlgo'] == sa]
    if sa == 'MergeSort':
        df_algo = df_algo.loc[df_algo['insurance'] == True]
        df_algo = df_algo.loc[df_algo['nocopy'] == True]

    print(df_algo[["sorter", "nWords", "nRuns", "rawTime", "hitsMean", "comparesMean", "swapsMean"]])

    fig, ax_left = plt.subplots()
    sns.set_style("whitegrid")

    # Plotting the left y-axis
    sns.lineplot(x='nWords', y='rawTime', data=df_algo, color='blue', ax=ax_left, marker = 'o')
    ax_left.set_ylabel('Raw Time (ms)', color='blue')
    ax_left.tick_params(axis='y', colors='blue')

    # Create a second y-axis for the right-hand side
    ax_right = ax_left.twinx()

    # Plotting the right y-axis data
    sns.lineplot(x='nWords', y='hitsMean', data=df_algo, color='green', ax=ax_right, label='Hits', marker = 'o')
    sns.lineplot(x='nWords', y='comparesMean', data=df_algo, color='red', ax=ax_right, label='Compares', marker = 'o')
    sns.lineplot(x='nWords', y='swapsMean', data=df_algo, color='purple', ax=ax_right, label='Swaps', marker = 'o')
    if sa == 'MergeSort':
        sns.lineplot(x='nWords', y='copies', data=df_algo, color='orange', ax=ax_right, label='Copies', marker = 'o')

    ax_left.set_xlabel('Number of Elements', color='black')
    ax_right.set_ylabel('Predictors', color='black')
    ax_right.tick_params(axis='y')

    plt.xscale('log', base=2)
    ax_left.set_yscale('log', base=2)
    ax_right.set_yscale('log', base=2)

    ax_left.set_ylim(2**0, 2**13)
    ax_right.set_ylim(2**13, 2**26)

    # Add a legend for the right y-axis
    ax_right.legend()

    # Adding titles and labels
    plt.title('Number of Elements vs. Raw Time vs different Predictors')
    plt.xlabel('Number of Elements')
    plt.legend(title='Predictors')
    # Display the plot
    plt_filename = os.path.join(file_dir, "{}TimePredictors.pdf".format(sa))
    plt.savefig(plt_filename, bbox_inches='tight')
    #plt.show()
    plt.close()


for predictor in predictors:
    print("Predictor = ",predictor)
    df_algo = df.loc[df['instrumented'] == True]
    df_algo = df_algo[(df_algo['sortAlgo'] != 'MergeSort') | ((df_algo['sortAlgo'] == 'MergeSort') & (df_algo['insurance'] == True) & (df_algo['nocopy'] == True))]

    fig, ax_left = plt.subplots()
    ax_right = ax_left.twinx()
    sns.set_style("whitegrid")

    algo_colors = ['red', 'green', 'blue']
    for i, algo in enumerate(sorting_alogs):
        df_sub_algo = df_algo.loc[df_algo['sortAlgo'] == algo]
        sb = sns.lineplot(x='nWords', y='rawTime', data=df_sub_algo, color=algo_colors[i], ax=ax_left, marker = 'o')
        ax_left.lines[i].set_linestyle("--")
        sns.lineplot(x='nWords', y=predictor, data=df_sub_algo, color=algo_colors[i], ax=ax_right, label=algo, marker = 'o')
        #sns.move_legend(sb, "upper center")

    ax_left.set_ylabel('Raw Time (ms) (- - - -)', color='black')
    ax_left.set_xlabel('Number of Elements', color='black')
    ax_right.set_ylabel('Predictor = {}'.format(predictor), color='black')
    ax_right.tick_params(axis='y')

    plt.xscale('log', base=2)
    ax_left.set_yscale('log', base=2)
    ax_right.set_yscale('log', base=2)

    ax_left.set_ylim(2**0, 2**13)
    ax_right.set_ylim(2**13, 2**26)

    # Add a legend for the right y-axis
    ax_right.legend()

    # Adding titles and labels
    plt.title('Number of Elements vs. Raw Time vs Sorting Algorithm')
    plt.xlabel('Number of Elements')
    plt.legend(title = "Sorting Algorithm", loc='upper left')
    # Display the plot
    plt_filename = os.path.join(file_dir, "{}TimeAlgos.pdf".format(predictor))
    plt.savefig(plt_filename, bbox_inches='tight')
    #plt.show()
    plt.close()
