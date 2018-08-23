import axios from 'axios';

import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

export const ACTION_TYPES = {
  RUN_QUERY: 'api/query',
  SCHEDULE_QUERY: 'api/schedule'
};

const initialState = {
  loading: false,
  errorMessage: null,
  updateSuccess: false,
  updateFailure: false
};

export type QueryState = Readonly<typeof initialState>;

// Reducer
export default (state: QueryState = initialState, action): QueryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.RUN_QUERY):
      return {
        ...initialState,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case FAILURE(ACTION_TYPES.RUN_QUERY):
      return {
        ...initialState,
        loading: false,
        updateSuccess: false,
        updateFailure: true
      };
    case SUCCESS(ACTION_TYPES.RUN_QUERY):
      return {
        ...initialState,
        loading: false,
        updateSuccess: true,
        updateFailure: false
      };
    default:
      return state;
  }
};

export const runQuery = query => ({
  type: ACTION_TYPES.RUN_QUERY,
  payload: axios.post('api/query', { query }),
  meta: {
    successMessage: '<strong>Query executed</strong>',
    errorMessage: '<strong>Error Running Query</strong> no data will be returned.'
  }
});

export const scheduleQuery = (timeMeasure, frequencyValue) => ({
  type: ACTION_TYPES.SCHEDULE_QUERY,
  payload: axios.put('api/schedule', { timeMeasure, frequencyValue }),
  meta: {
    successMessage: '<strong>Query scheduled</strong>',
    errorMessage: '<strong>Error Scheduling Query</strong> no schedule was created.'
  }
});
