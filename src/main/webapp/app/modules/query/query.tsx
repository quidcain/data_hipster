import React from 'react';

import { connect } from 'react-redux';
import { AvForm, AvField } from 'availity-reactstrap-validation';
import { Row, Col, Button } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';
import { runQuery } from './query.reducer';

export interface IQueryProps extends StateProps, DispatchProps {}

export interface IQueryState {
  password: string;
}

export class QueryPage extends React.Component<IQueryProps, IQueryState> {
  state: IQueryState = {
    password: ''
  };

  componentDidMount() {
    this.props.getSession();
  }

  componentWillUnmount() {}

  handleValidSubmit = (event, values) => {
    this.props.runQuery(values.currentPassword, values.newPassword);
  };

  render() {
    const { account } = this.props;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="password-title">Password for {account.login}</h2>
            <AvForm id="password-form" onValidSubmit={this.handleValidSubmit}>
              <AvField
                name="currentPassword"
                label="Current password"
                placeholder="Current password"
                type="password"
                validate={{
                  required: { value: true, errorMessage: 'Your password is required.' }
                }}
              />

              <AvField
                name="confirmPassword"
                label="New password confirmation"
                placeholder="Confirm the new password"
                type="password"
                validate={{
                  required: {
                    value: true,
                    errorMessage: 'Your confirmation password is required.'
                  },
                  minLength: {
                    value: 4,
                    errorMessage: 'Your confirmation password is required to be at least 4 characters.'
                  },
                  maxLength: {
                    value: 50,
                    errorMessage: 'Your confirmation password cannot be longer than 50 characters.'
                  },
                  match: {
                    value: 'newPassword',
                    errorMessage: 'The password and its confirmation do not match!'
                  }
                }}
              />
              <Button color="success" type="submit">
                Save
              </Button>
            </AvForm>
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = ({ authentication }: IRootState) => ({
  account: authentication.account,
  isAuthenticated: authentication.isAuthenticated
});

const mapDispatchToProps = { getSession, runQuery };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(QueryPage);
