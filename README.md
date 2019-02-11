#Time Ranges Subtraction

## Coding problem
Write a program that will subtract one list of time ranges from another. Formally: for two lists of time ranges A and B, a time is in (A-B) if and only if it is part of A and not part of B.

A time range has a start time and an end time. You can define times and time ranges however you want (Unix timestamps, date/time objects in your preferred language, the actual string “start-end”, etc).

Your solution shouldn’t rely on the granularity of the timestamps (so don’t, for example, iterate over all the times in all the ranges and check to see if that time is “in”).

## Algorithm phases:
1. Sorting of A and B
2. Merging of overlapping ranges withing A and B
3. Filtering of empty and negative ranges withing A and B
4. Subtraction (A-B)

## Assumptions and limitations:
1. The regular timeline is assumed over "Groundhog Day" one (time is not going in a circle within the same date after midnight). Therefore date **and** time should be provided on the input for time ranges. 
2. Input may or may not be sorted. Subtraction algorithm itself has linear complexity but requires pre-sorting. Therefore overall complexity is O(nlogn). 
3. Input may or may not contain overlapping ranges (e.g. A = {09:30-09:45, 09:15-10:00}). Such ranges are merged.
4. Input may or may not contain empty ranges (e.g., from 09:30 till 09:30 of the same day). Such ranges are filtered.
5. Input may or may not contain negative ranges (e.g, from 12:00 till 11:00 of the same day). Such ranges are filtered. 