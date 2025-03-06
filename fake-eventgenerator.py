import pandas as pd
import random
import datetime
from faker import Faker  # type: ignore

fake = Faker()

# Define event types
event_types = ["Conference", "Wedding", "Workshop", "Party"]

# Function to generate random event data
def generate_event_data(num_records=100):
    data = []
    for _ in range(num_records):
        event_type = random.choice(event_types)
        location = fake.city()
        
        # Use datetime.date() and format it as a string
        date = fake.date_between(start_date=datetime.date(2025, 1, 1), end_date=datetime.date(2025, 12, 31)).strftime("%Y-%m-%d")
        
        description = fake.sentence(nb_words=8)
        event_name = f"{event_type} - {fake.company()}"

        data.append([event_type, event_name, location, date, description])  # Date is now a string
    
    return pd.DataFrame(data, columns=["Event Type", "Event Name", "Location", "Date", "Description"])

# Generate dataset
df = generate_event_data(100)

# Save to CSV (fix date formatting issue)
df.to_csv("event_data_new.csv", index=False)

# Display first few rows to verify
print(df.head())
