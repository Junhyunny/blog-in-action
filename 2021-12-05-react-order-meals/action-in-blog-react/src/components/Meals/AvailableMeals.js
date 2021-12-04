import Card from '../UI/Card';
import MealItem from './MealItem/MealItem';
import classes from './AvailableMeals.module.css';
import { useEffect, useState } from 'react';

const AvailableMeals = () => {

  const [meals, setMeals] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [httpError, setHttpError] = useState(null);

  useEffect(() => {
    const fetchMeals = async () => {
      setIsLoading(true);
      const mealArray = [];
      const response = await fetch('https://your.firebaseio.com/meals.json');
      if (!response.ok) {
        throw new Error('Something went wrong.');
      }
      const data = await response.json();
      for (const key in data) {
        let meal = {
          ...data[key],
          id: key
        };
        mealArray.push(meal);
      }
      setMeals(mealArray);
      setIsLoading(false);
    };
    fetchMeals().catch(error => {
      setHttpError(error.message);
      setIsLoading(false);
    });
  }, []);

  const mealsList = meals.map((meal) => (
    <MealItem
      key={meal.id}
      id={meal.id}
      name={meal.name}
      description={meal.description}
      price={meal.price}
    />
  ));

  if (isLoading) {
    return (
      <section className={classes.mealsLoading}>
        <p>
          Loading...
        </p>
      </section >
    );
  }

if (httpError) {
  return (
    <section className={classes.mealsError}>
      <p>
        {httpError}
      </p>
    </section>
  );
}

return (
  <section className={classes.meals}>
    <Card>
      <ul>{mealsList}</ul>
    </Card>
  </section>
);
};

export default AvailableMeals;
