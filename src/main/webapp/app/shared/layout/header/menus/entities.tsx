import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Actions" id="entity-menu">
    <DropdownItem tag={Link} to="/query">
      <FontAwesomeIcon icon="clock" /> Run/Schedule Query
    </DropdownItem>
    <DropdownItem tag={Link} to="/datasource">
      <FontAwesomeIcon icon="clock" /> Data Sources
    </DropdownItem>
  </NavDropdown>
);
