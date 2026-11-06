/*
 * SPDX-FileCopyrightText: Copyright © 2026 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LookupController {

  private final DataSource dataSource;

  public LookupController(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @GetMapping("/demo/lookup")
  public String lookup(@RequestParam String name) throws Exception {
    try (Connection c = dataSource.getConnection();
        Statement s = c.createStatement()) {
      // Vulnerable: user input concatenated into the SQL string
      ResultSet rs = s.executeQuery("SELECT * FROM users WHERE name = '" + name + "'");
      return rs.next() ? "found" : "not found";
    }
  }
}
